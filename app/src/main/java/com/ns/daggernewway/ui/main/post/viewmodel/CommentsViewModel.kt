package com.ns.daggernewway.ui.main.post.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.ns.archcomponents.annotations.ConstructorPriority
import com.ns.archcomponents.annotations.GenerateFactory
import com.ns.daggernewway.domain.ui.entity.Comment
import com.ns.daggernewway.ui.base.viewmodel.BaseCoroutineSavableViewModel
import com.ns.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus
import com.ns.daggernewway.usecase.getcomments.IGetCommentsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@GenerateFactory
class CommentsViewModel : BaseCoroutineSavableViewModel, ICommentsViewModel {

    companion object {
        private const val KEY_POST_ID = "post"
    }

    override val commentsLoadStatus = MutableLiveData<GeneralFlowStatus>()

    override val comments = object : MutableLiveData<List<Comment>>() {

        private var triggered = false

        override fun onActive() {
            super.onActive()
            if (!triggered) {
                triggered = true
                loadComments()
            }
        }
    }

    private var postId: Int = 0

    private val useCase: IGetCommentsUseCase

    private var loadJob: Job? = null

    @ConstructorPriority(1)
    constructor(useCase: IGetCommentsUseCase,
                postId: Int) : super() {
        this.useCase = useCase
        this.postId = postId
    }

    @ConstructorPriority(0)
    constructor(useCase: IGetCommentsUseCase,
                savedInstanceState: Bundle) : super(savedInstanceState = savedInstanceState) {
        this.useCase = useCase
    }

    override fun refreshData() = loadComments()

    override fun moveLoadStatusToIdle() {
        commentsLoadStatus.value = GeneralFlowStatus.IDLE
    }

    override fun saveInner(bundle: Bundle) {
        bundle.putInt(KEY_POST_ID, postId)
    }

    override fun restoreInner(bundle: Bundle) {
        postId = bundle.getInt(KEY_POST_ID)
    }

    private fun loadComments() {
        if (loadJob?.isActive == true) return
        loadJob = launch {
            val result = withContext(Dispatchers.IO) { useCase.getComments(postId) }
            when (result) {
                is IGetCommentsUseCase.GetCommentsResult.Success -> {
                    commentsLoadStatus.value = GeneralFlowStatus.COMPLETED
                    comments.value = result.data
                }
                is IGetCommentsUseCase.GetCommentsResult.Failed -> {
                    commentsLoadStatus.value = GeneralFlowStatus.FAILED
                }
            }
        }
    }

}