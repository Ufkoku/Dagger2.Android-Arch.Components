package com.ufkoku.daggernewway.ui.main.feed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ufkoku.archcomponents.DaggerViewModel
import com.ufkoku.daggernewway.domain.ui.entity.Post
import com.ufkoku.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus
import com.ufkoku.daggernewway.usecase.getfeed.IGetFeedUseCase
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedViewModel(hasAndroidInjector: HasAndroidInjector)
    : DaggerViewModel(hasAndroidInjector), IFeedViewModel {

    override val feedLoadStatus = MutableLiveData<GeneralFlowStatus>()

    override val feed = object : MutableLiveData<List<Post>>() {

        private var triggered: Boolean = false

        override fun onActive() {
            super.onActive()
            if (!triggered) {
                triggered = true
                loadFeed()
            }
        }

    }

    @Inject
    protected lateinit var useCase: IGetFeedUseCase

    private var loadFeedJob: Job? = null

    override fun moveLoadStatusToIdle() {
        feedLoadStatus.value = GeneralFlowStatus.IDLE
    }

    override fun refreshData() = loadFeed()

    private fun loadFeed() {
        if (loadFeedJob?.isActive == true) return
        loadFeedJob = viewModelScope.launch {
            feedLoadStatus.value = GeneralFlowStatus.IN_PROGRESS
            when (val result = withContext(Dispatchers.IO) { useCase.getFeed() }) {

                is IGetFeedUseCase.GetFeedResult.Success -> {
                    feedLoadStatus.value = GeneralFlowStatus.COMPLETED
                    feed.value = result.data
                }

                is IGetFeedUseCase.GetFeedResult.Failed -> {
                    feedLoadStatus.value = GeneralFlowStatus.FAILED
                }

            }
        }
    }

}