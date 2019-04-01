package com.ns.daggernewway.ui.main.post.viewmodel

import androidx.lifecycle.LiveData
import com.ns.daggernewway.domain.ui.entity.Comment
import com.ns.daggernewway.domain.ui.entity.Post
import com.ns.daggernewway.ui.common.viewmodel.status.GeneralFlowStatus

interface ICommentsViewModel {

    val commentsLoadStatus: LiveData<GeneralFlowStatus>

    val post: LiveData<Post>

    val comments: LiveData<List<Comment>>

    val accumulatedData: LiveData<AccumulatedData>

    fun refreshData()

    fun moveLoadStatusToIdle()

    data class AccumulatedData(val post: Post?, val comments: List<Comment>?)

}