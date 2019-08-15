package com.ufkoku.daggernewway.ui.main.post.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import com.ufkoku.archcomponents.DaggerSavableViewModel
import com.ufkoku.daggernewway.domain.ui.entity.Comment
import com.ufkoku.daggernewway.domain.ui.entity.Post
import com.ufkoku.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus
import com.ufkoku.daggernewway.usecase.getcomments.IGetCommentsUseCase
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


class CommentsViewModel(hasAndroidInjector: HasAndroidInjector, savedStateHandle: SavedStateHandle)
    : DaggerSavableViewModel(hasAndroidInjector, savedStateHandle), ICommentsViewModel {

    companion object {
        private const val KEY_POST = "argPost"

        fun buildDefaultValues(post: Post?): Bundle = Bundle().apply {
            if (post != null) putParcelable(KEY_POST, post)
        }

    }

    override val commentsLoadStatus = MutableLiveData<GeneralFlowStatus>()

    override val post: LiveData<Post> = savedStateHandle.getLiveData<Post>(KEY_POST)

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

    @Inject
    protected lateinit var useCase: IGetCommentsUseCase

    private val postId: Int
        get() = savedStateHandle.get<Post>(KEY_POST)!!.id

    private var loadJob: Job? = null

    override fun refreshData() = loadComments()

    override fun moveLoadStatusToIdle() {
        commentsLoadStatus.value = GeneralFlowStatus.IDLE
    }

    private fun loadComments() {
        if (loadJob?.isActive == true) return
        loadJob = viewModelScope.launch {
            when (val result = useCase.getComments(postId)) {
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