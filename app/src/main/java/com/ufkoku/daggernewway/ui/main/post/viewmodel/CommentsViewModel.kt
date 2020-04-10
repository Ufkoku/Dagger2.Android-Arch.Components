package com.ufkoku.daggernewway.ui.main.post.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import com.ufkoku.archcomponents.annotations.DefaultValuesProvider
import com.ufkoku.archcomponents.annotations.GenerateFactory
import com.ufkoku.daggernewway.domain.ui.entity.Comment
import com.ufkoku.daggernewway.domain.ui.entity.Post
import com.ufkoku.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus
import com.ufkoku.daggernewway.usecase.GetCommentsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface CommentsViewModel {

    val commentsLoadStatus: LiveData<GeneralFlowStatus>

    val post: LiveData<Post>

    val comments: LiveData<List<Comment>>

    val accumulatedData: LiveData<AccumulatedData>

    fun refreshData()

    fun moveLoadStatusToIdle()

    data class AccumulatedData(val post: Post?, val comments: List<Comment>?)

}

@GenerateFactory
class CommentsViewModelImpl(private val useCase: GetCommentsUseCase,
                            private val savedState: SavedStateHandle) : ViewModel(), CommentsViewModel {

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

    override val accumulatedData = MediatorLiveData<CommentsViewModel.AccumulatedData>().apply {
        addSource(post) { value = CommentsViewModel.AccumulatedData(it, value?.comments) }
        addSource(comments) { value = CommentsViewModel.AccumulatedData(value?.post, it) }
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
        loadJob = viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { useCase.getComments(postId) }
            when (result) {
                is GetCommentsUseCase.GetCommentsResult.Success -> {
                    commentsLoadStatus.value = GeneralFlowStatus.COMPLETED
                    comments.value = result.data
                }
                is GetCommentsUseCase.GetCommentsResult.Failed -> {
                    commentsLoadStatus.value = GeneralFlowStatus.FAILED
                }
            }
        }
    }

}