package com.ufkoku.daggernewway.di.domain.mapper

import com.ufkoku.daggernewway.domain.mapper.comment.CommentMapper
import com.ufkoku.daggernewway.domain.mapper.comment.ICommentMapper
import dagger.Module
import dagger.Provides

@Module
class CommentMapperModule {

    @Provides
    fun provideCommentMapper(): ICommentMapper = CommentMapper()

}