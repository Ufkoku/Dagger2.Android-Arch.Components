package com.ufkoku.daggernewway.usecase.getcomments

import com.ufkoku.daggernewway.domain.mapper.comment.ICommentMapper
import com.ufkoku.daggernewway.domain.rest.NetworkApi
import com.ufkoku.daggernewway.usecase.getcomments.IGetCommentsUseCase.GetCommentsResult

class GetCommentsUseCase(private val networkApi: NetworkApi,
                         private val commentsMapper: ICommentMapper) : IGetCommentsUseCase {

    override suspend fun getComments(postId: Int): GetCommentsResult {
        val result = runCatching { networkApi.getPostComments(postId) }
        return if (result.isSuccess) {
            GetCommentsResult.Success(result.getOrThrow().map { commentsMapper.mapRestComment(it) })
        } else {
            GetCommentsResult.Failed
        }
    }

}