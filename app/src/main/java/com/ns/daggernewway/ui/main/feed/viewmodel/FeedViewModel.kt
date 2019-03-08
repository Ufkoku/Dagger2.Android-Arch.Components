package com.ns.daggernewway.ui.main.feed.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ns.archcomponents.annotations.GenerateFactory
import com.ns.daggernewway.domain.ui.entity.Post
import com.ns.daggernewway.ui.base.viewmodel.BaseCoroutineViewModel
import com.ns.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus
import com.ns.daggernewway.usecase.getfeed.IGetFeedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@GenerateFactory(inject = true)
class FeedViewModel(private val useCase: IGetFeedUseCase) : BaseCoroutineViewModel(), IFeedViewModel {

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

    private var loadFeedJob: Job? = null

    override fun moveLoadStatusToIdle() {
        feedLoadStatus.value = GeneralFlowStatus.IDLE
    }

    override fun refreshData() = loadFeed()

    private fun loadFeed() {
        if (loadFeedJob?.isActive == true) return
        loadFeedJob = launch {
            feedLoadStatus.value = GeneralFlowStatus.IN_PROGRESS

            val result = withContext(Dispatchers.IO) { useCase.getFeed() }
            when (result) {

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