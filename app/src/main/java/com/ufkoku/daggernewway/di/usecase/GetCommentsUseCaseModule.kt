package com.ufkoku.daggernewway.di.usecase

import com.ufkoku.daggernewway.di.domain.mapper.CommentMapperModule
import com.ufkoku.daggernewway.domain.mapper.comment.ICommentMapper
import com.ufkoku.daggernewway.domain.rest.NetworkApi
import com.ufkoku.daggernewway.usecase.getcomments.GetCommentsUseCase
import com.ufkoku.daggernewway.usecase.getcomments.IGetCommentsUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [CommentMapperModule::class])
class GetCommentsUseCaseModule {

    @Provides
    fun provideGetCommentsInteractor(networkApi: NetworkApi,
                                     commentsMapper: ICommentMapper): IGetCommentsUseCase {
        return GetCommentsUseCase(networkApi, commentsMapper)
    }

}