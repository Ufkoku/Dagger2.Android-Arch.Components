package com.ns.daggernewway.di.usecase

import com.ns.daggernewway.di.domain.mapper.PostMapperModule
import com.ns.daggernewway.domain.mapper.post.IPostMapper
import com.ns.daggernewway.domain.rest.NetworkApi
import com.ns.daggernewway.usecase.getfeed.GetFeedUseCase
import com.ns.daggernewway.usecase.getfeed.IGetFeedUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [PostMapperModule::class])
class GetFeedUseCaseModule {

    @Provides
    fun provideGetFeedInteractor(networkApi: NetworkApi,
                                 postMapper: IPostMapper): IGetFeedUseCase {
        return GetFeedUseCase(networkApi, postMapper)
    }

}
