package com.ns.daggernewway.ui.main.feed.viewmodel

import androidx.lifecycle.LiveData
import com.ns.daggernewway.domain.ui.entity.Post
import com.ns.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus

interface IFeedViewModel {

    val feedLoadStatus: LiveData<GeneralFlowStatus>

    val feed: LiveData<List<Post>>

    fun refreshData()

    fun moveLoadStatusToIdle()

}