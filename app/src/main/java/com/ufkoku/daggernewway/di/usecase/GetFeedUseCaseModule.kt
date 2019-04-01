package com.ufkoku.daggernewway.di.usecase

import com.ufkoku.daggernewway.di.domain.mapper.PostMapperModule
import com.ufkoku.daggernewway.domain.mapper.post.IPostMapper
import com.ufkoku.daggernewway.domain.rest.NetworkApi
import com.ufkoku.daggernewway.usecase.getfeed.GetFeedUseCase
import com.ufkoku.daggernewway.usecase.getfeed.IGetFeedUseCase
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
