package com.ns.daggernewway.di.usecase

import com.ns.daggernewway.di.domain.mapper.CommentMapperModule
import com.ns.daggernewway.domain.mapper.comment.ICommentMapper
import com.ns.daggernewway.domain.rest.NetworkApi
import com.ns.daggernewway.usecase.getcomments.GetCommentsUseCase
import com.ns.daggernewway.usecase.getcomments.IGetCommentsUseCase
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