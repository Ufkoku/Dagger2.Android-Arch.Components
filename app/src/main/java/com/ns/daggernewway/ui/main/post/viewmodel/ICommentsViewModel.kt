package com.ns.daggernewway.ui.main.post.viewmodel

import androidx.lifecycle.LiveData
import com.ns.daggernewway.domain.ui.entity.Comment
import com.ns.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus

interface ICommentsViewModel {

    val commentsLoadStatus: LiveData<GeneralFlowStatus>

    val comments: LiveData<List<Comment>>

    fun refreshData()

    fun moveLoadStatusToIdle()

}