package com.ns.daggernewway.ui.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ns.archcomponents.annotations.GenerateFactory
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.interactor.getfeed.IGetFeedInteractor
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.io.IOException

@GenerateFactory(inject = true)
class FeedViewModel(private val interactor: IGetFeedInteractor) : ViewModel() {

    private lateinit var feed: MutableLiveData<List<FullPost>>

    private var loadFeedJob: Job? = null

    fun getFeed(): LiveData<List<FullPost>> {
        if (!::feed.isInitialized) {
            feed = MutableLiveData()
            loadFeed()
        }
        return feed
    }

    private fun loadFeed() {
        loadFeedJob?.cancel()

        loadFeedJob = launch(UI) {
            try {
                feed.value = withContext(CommonPool) { interactor.getFeed() }
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadFeedJob?.cancel()
    }

}