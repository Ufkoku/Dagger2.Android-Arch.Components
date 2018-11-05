package com.ns.daggernewway.ui.common.viewmodel.postcomments

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ns.archcomponents.annotations.ConstructorPriority
import com.ns.archcomponents.annotations.GenerateFactory
import com.ns.daggernewway.entity.rest.Comment
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.interactor.getcomments.IGetCommentsInteractor
import com.ns.daggernewway.ui.common.viewmodel.status.IStatus
import com.ns.daggernewway.ui.common.viewmodel.status.Status
import com.ufkoku.archcomponents.SavableViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

private const val KEY_POST = "fullPost"

@GenerateFactory
class CommentsViewModel : SavableViewModel {

    lateinit var post: FullPost
        private set

    private val interactor: IGetCommentsInteractor

    private lateinit var comments: MutableLiveData<IStatus<List<Comment>>>

    private var loadJob: Job? = null

    @ConstructorPriority(1)
    constructor(interactor: IGetCommentsInteractor,
                post: FullPost) : super() {
        this.interactor = interactor
        this.post = post
    }

    @ConstructorPriority(0)
    constructor(interactor: IGetCommentsInteractor,
                savedInstanceState: Bundle) : super(savedInstanceState = savedInstanceState) {
        this.interactor = interactor
    }

    override fun saveInner(bundle: Bundle) {
        bundle.putParcelable(KEY_POST, post)
    }

    override fun restoreInner(bundle: Bundle) {
        post = bundle.getParcelable(KEY_POST)!!
    }

    fun getComments(): LiveData<IStatus<List<Comment>>> {
        if (!::comments.isInitialized) {
            comments = MutableLiveData()
            loadComments()
        }
        return comments
    }

    private fun loadComments() {
        loadJob?.cancel()

        comments.value = Status.inProgress()
        loadJob = launch(UI) {
            val result = withContext(CommonPool) { interactor.getComments(post.id) }
            if (result.isSuccess) {
                comments.value = Status.completed(result.data!!)
            } else {
                comments.value = Status.error("Error Message")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadJob?.cancel()
    }

}