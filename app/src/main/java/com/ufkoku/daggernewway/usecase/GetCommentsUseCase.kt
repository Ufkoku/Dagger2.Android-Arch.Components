package com.ufkoku.daggernewway.usecase

import com.ufkoku.daggernewway.domain.mapper.CommentMapper
import com.ufkoku.daggernewway.domain.rest.NetworkApi
import com.ufkoku.daggernewway.domain.ui.entity.Comment
import com.ufkoku.daggernewway.usecase.GetCommentsUseCase.GetCommentsResult

interface GetCommentsUseCase {

    suspend fun getComments(postId: Int): GetCommentsResult

    sealed class GetCommentsResult {
        class Success(val data: List<Comment>) : GetCommentsResult()
        object Failed : GetCommentsResult()
    }

}

class GetCommentsUseCaseImpl(private val networkApi: NetworkApi,
                             private val commentsMapper: CommentMapper) : GetCommentsUseCase {

    override suspend fun getComments(postId: Int): GetCommentsResult {
        val result = runCatching { networkApi.getPostComments(postId) }
        return if (result.isSuccess) {
            GetCommentsResult.Success(result.getOrThrow().map { commentsMapper.mapRestComment(it) })
        } else {
            GetCommentsResult.Failed
        }
    }

}