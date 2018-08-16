package com.ns.daggernewway.di.interactor

import com.ns.daggernewway.interactor.getfeed.GetFeedInteractor
import com.ns.daggernewway.interactor.getfeed.IGetFeedInteractor
import com.ns.daggernewway.rest.NetworkApi
import dagger.Module
import dagger.Provides

@Module
class GetFeedInteractorModule {

    @Provides
    fun provideGetFeedInteractor(networkApi: NetworkApi): IGetFeedInteractor {
        return GetFeedInteractor(networkApi)
    }

}
