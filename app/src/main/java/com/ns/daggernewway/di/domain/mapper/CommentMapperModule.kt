package com.ns.daggernewway.di.domain.mapper

import com.ns.daggernewway.domain.mapper.comment.CommentMapper
import com.ns.daggernewway.domain.mapper.comment.ICommentMapper
import dagger.Module
import dagger.Provides

@Module
class CommentMapperModule {

    @Provides
    fun provideCommentMapper(): ICommentMapper = CommentMapper()

}