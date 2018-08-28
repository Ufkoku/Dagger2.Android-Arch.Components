package com.ns.daggernewway.ui.main.post

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ns.daggernewway.entity.rest.Comment
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.interactor.getcomments.IGetCommentsInteractor
import com.ufkoku.archcomponents.SavableViewModel
import com.ufkoku.mvp.viewstate.autosavable.AutoSavable
import com.ufkoku.mvp.viewstate.autosavable.DontSave
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.io.IOException

@AutoSavable(includeSuper = false)
class CommentsViewModel : SavableViewModel {

    lateinit var post: FullPost
        private set

    @DontSave
    private val interactor: IGetCommentsInteractor

    @DontSave
    private lateinit var comments: MutableLiveData<List<Comment>>

    @DontSave
    private var loadJob: Job? = null

    constructor(interactor: IGetCommentsInteractor,
                post: FullPost) : super() {
        this.interactor = interactor
        this.post = post
    }

    constructor(interactor: IGetCommentsInteractor,
                savedInstanceState: Bundle) : super(savedInstanceState) {
        this.interactor = interactor
    }

    override fun saveInner(bundle: Bundle) {
        CommentsViewModelSaver.save(this, bundle)
    }

    override fun restoreInner(bundle: Bundle) {
        CommentsViewModelSaver.restore(this, bundle)
    }

    fun getComments(): LiveData<List<Comment>> {
        if (!::comments.isInitialized) {
            comments = MutableLiveData()
            loadComments()
        }
        return comments
    }

    private fun loadComments() {
        loadJob?.cancel()

        loadJob = launch(UI) {
            try {
                comments.value = withContext(CommonPool) { interactor.getComments(post.id) }
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadJob?.cancel()
    }

}