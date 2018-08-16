package com.ns.daggernewway.di.interactor

import com.ns.daggernewway.interactor.getcomments.GetCommentsInteractor
import com.ns.daggernewway.interactor.getcomments.IGetCommentsInteractor
import com.ns.daggernewway.rest.NetworkApi
import dagger.Module
import dagger.Provides

@Module
class GetCommentsInteractorModule {

    @Provides
    fun provideGetCommentsInteractor(networkApi: NetworkApi): IGetCommentsInteractor {
        return GetCommentsInteractor(networkApi)
    }

}