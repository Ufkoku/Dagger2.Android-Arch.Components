package com.ufkoku.daggernewway.ui.main.post.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.ufkoku.archcomponents.annotations.DefaultValuesProvider
import com.ufkoku.archcomponents.annotations.GenerateFactory
import com.ufkoku.daggernewway.domain.ui.entity.Comment
import com.ufkoku.daggernewway.domain.ui.entity.Post
import com.ufkoku.daggernewway.ui.base.viewmodel.BaseCoroutineViewModel
import com.ufkoku.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus
import com.ufkoku.daggernewway.usecase.getcomments.IGetCommentsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@GenerateFactory
class CommentsViewModel(private val useCase: IGetCommentsUseCase,
                        private val savedState: SavedStateHandle) : BaseCoroutineViewModel(), ICommentsViewModel {

    companion object {
        private const val KEY_POST = "argPost"

        @JvmStatic
        @DefaultValuesProvider
        fun buildDefaultValues(post: Post?): Bundle = Bundle().apply {
            if (post != null) putParcelable(KEY_POST, post)
        }

    }

    override val commentsLoadStatus = MutableLiveData<GeneralFlowStatus>()

    override val post: LiveData<Post> = savedState.getLiveData<Post>(KEY_POST)

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

    override val accumulatedData = MediatorLiveData<ICommentsViewModel.AccumulatedData>().apply {
        addSource(post) { value = ICommentsViewModel.AccumulatedData(it, value?.comments) }
        addSource(comments) { value = ICommentsViewModel.AccumulatedData(value?.post, it) }
    }

    private val postId: Int
        get() = savedState.get<Post>(KEY_POST)!!.id

    private var loadJob: Job? = null

    override fun refreshData() = loadComments()

    override fun moveLoadStatusToIdle() {
        commentsLoadStatus.value = GeneralFlowStatus.IDLE
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