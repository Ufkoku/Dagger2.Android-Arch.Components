package com.ns.daggernewway.interactor.getfeed

import com.ns.daggernewway.entity.ui.FullPost

interface IGetFeedInteractor {

    suspend fun getFeed(): List<FullPost>

}