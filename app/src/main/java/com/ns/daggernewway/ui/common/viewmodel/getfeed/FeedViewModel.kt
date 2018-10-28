package com.ns.daggernewway.ui.common.viewmodel.getfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ns.archcomponents.annotations.GenerateFactory
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.interactor.getfeed.IGetFeedInteractor
import com.ns.daggernewway.ui.common.viewmodel.status.IStatus
import com.ns.daggernewway.ui.common.viewmodel.status.Status
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

@GenerateFactory(inject = true)
class FeedViewModel(private val interactor: IGetFeedInteractor) : ViewModel() {

    private lateinit var feed: MutableLiveData<IStatus<List<FullPost>>>

    private var loadFeedJob: Job? = null

    fun getFeed(): LiveData<IStatus<List<FullPost>>> {
        if (!::feed.isInitialized) {
            feed = MutableLiveData()
            loadFeed()
        }
        return feed
    }

    private fun loadFeed() {
        loadFeedJob?.cancel()

        feed.value = Status.inProgress()
        loadFeedJob = launch(UI) {
            val result = withContext(CommonPool) { interactor.getFeed() }
            if (result.isSuccess) {
                feed.value = Status.completed(result.data!!)
            } else {
                feed.value = Status.error("Error Message")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadFeedJob?.cancel()
    }

    data class GetFeedStatus(val status: Status, val data: List<FullPost>?) {

        enum class Status {
            ERROR,
            IN_PROGRESS,
            COMPLETED
        }

    }

}