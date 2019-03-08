package com.ns.daggernewway.usecase.getfeed

import com.ns.daggernewway.domain.ui.entity.Post

interface IGetFeedUseCase {

    suspend fun getFeed(): GetFeedResult

    sealed class GetFeedResult {
        class Success(val data: List<Post>) : GetFeedResult()

        object Failed : GetFeedResult()
    }

}