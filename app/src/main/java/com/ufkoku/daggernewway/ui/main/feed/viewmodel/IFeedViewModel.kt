package com.ufkoku.daggernewway.ui.main.feed.viewmodel

import androidx.lifecycle.LiveData
import com.ufkoku.daggernewway.domain.ui.entity.Post
import com.ufkoku.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus

interface IFeedViewModel {

    val feedLoadStatus: LiveData<GeneralFlowStatus>

    val feed: LiveData<List<Post>>

    fun refreshData()

    fun moveLoadStatusToIdle()

}