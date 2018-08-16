package com.ns.daggernewway.ui.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.interactor.getfeed.IGetFeedInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class FeedViewModel(private val interactor: IGetFeedInteractor) : ViewModel() {

    private lateinit var liveData: MutableLiveData<List<FullPost>>

    private var loadFeedDisposable: Disposable? = null

    fun getFeed(): LiveData<List<FullPost>> {
        if (!::liveData.isInitialized) {
            liveData = MutableLiveData()
            loadFeed()
        }
        return liveData
    }

    private fun loadFeed() {
        loadFeedDisposable?.dispose()

        loadFeedDisposable = interactor.getFeed()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { res, ex ->
                    if (res != null) {
                        liveData.value = res
                    } else if (ex != null) {
                        //handle ex
                    }
                }

    }

    override fun onCleared() {
        super.onCleared()
        loadFeedDisposable?.dispose()
    }

}