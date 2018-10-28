package com.ns.daggernewway.interactor.getfeed

import com.ns.daggernewway.entity.ui.FullPost

interface IGetFeedInteractor {

    suspend fun getFeed(): GetFeedResult

    data class GetFeedResult(val isSuccess: Boolean,
                             val data: List<FullPost>?)

}