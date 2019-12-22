package com.ufkoku.daggernewway.di.usecase

import com.ufkoku.daggernewway.domain.mapper.comment.ICommentMapper
import com.ufkoku.daggernewway.domain.mapper.post.IPostMapper
import com.ufkoku.daggernewway.domain.rest.NetworkApi
import com.ufkoku.daggernewway.usecase.getcomments.GetCommentsUseCase
import com.ufkoku.daggernewway.usecase.getcomments.IGetCommentsUseCase
import com.ufkoku.daggernewway.usecase.getfeed.GetFeedUseCase
import com.ufkoku.daggernewway.usecase.getfeed.IGetFeedUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetCommentsInteractor(networkApi: NetworkApi,
                                     commentsMapper: ICommentMapper): IGetCommentsUseCase {
        return GetCommentsUseCase(networkApi, commentsMapper)
    }

    @Provides
    fun provideGetFeedInteractor(networkApi: NetworkApi,
                                 postMapper: IPostMapper): IGetFeedUseCase {
        return GetFeedUseCase(networkApi, postMapper)
    }

}