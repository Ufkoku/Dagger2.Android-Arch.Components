package com.ufkoku.daggernewway.usecase.getfeed

import com.ufkoku.daggernewway.domain.ui.entity.Post

interface IGetFeedUseCase {

    suspend fun getFeed(): GetFeedResult

    sealed class GetFeedResult {
        class Success(val data: List<Post>) : GetFeedResult()

        object Failed : GetFeedResult()
    }

}