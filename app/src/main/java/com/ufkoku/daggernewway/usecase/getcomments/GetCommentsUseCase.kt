package com.ufkoku.daggernewway.usecase.getcomments

import com.ufkoku.daggernewway.domain.mapper.comment.ICommentMapper
import com.ufkoku.daggernewway.domain.rest.NetworkApi
import com.ufkoku.daggernewway.usecase.getcomments.IGetCommentsUseCase.GetCommentsResult

class GetCommentsUseCase(private val networkApi: NetworkApi,
                         private val commentsMapper: ICommentMapper) : IGetCommentsUseCase {

    override suspend fun getComments(postId: Int): GetCommentsResult {
        return try {
            val data = networkApi.getPostComments(postId).await()
            GetCommentsResult.Success(data.map { commentsMapper.mapRestComment(it) })
        } catch (ex: Throwable) {
            GetCommentsResult.Failed
        }
    }

}