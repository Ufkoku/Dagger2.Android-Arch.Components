package com.ns.daggernewway.interactor.getfeed

import com.ns.daggernewway.entity.ui.FullPost
import io.reactivex.Single

interface IGetFeedInteractor {

    fun getFeed(): Single<out List<FullPost>>

}