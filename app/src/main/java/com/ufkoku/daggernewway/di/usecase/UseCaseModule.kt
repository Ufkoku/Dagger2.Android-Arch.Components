package com.ufkoku.daggernewway.di.usecase

import com.ufkoku.daggernewway.domain.mapper.CommentMapper
import com.ufkoku.daggernewway.domain.mapper.PostMapper
import com.ufkoku.daggernewway.domain.rest.NetworkApi
import com.ufkoku.daggernewway.usecase.GetCommentsUseCase
import com.ufkoku.daggernewway.usecase.GetCommentsUseCaseImpl
import com.ufkoku.daggernewway.usecase.GetFeedUseCase
import com.ufkoku.daggernewway.usecase.GetFeedUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetCommentsInteractor(networkApi: NetworkApi,
                                     commentsMapper: CommentMapper): GetCommentsUseCase {
        return GetCommentsUseCaseImpl(networkApi, commentsMapper)
    }

    @Provides
    fun provideGetFeedInteractor(networkApi: NetworkApi,
                                 postMapper: PostMapper): GetFeedUseCase {
        return GetFeedUseCaseImpl(networkApi, postMapper)
    }

}