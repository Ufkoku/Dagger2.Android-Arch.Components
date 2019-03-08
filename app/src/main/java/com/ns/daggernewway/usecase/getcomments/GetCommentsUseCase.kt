package com.ns.daggernewway.usecase.getcomments

import com.ns.daggernewway.domain.mapper.comment.ICommentMapper
import com.ns.daggernewway.domain.rest.NetworkApi
import com.ns.daggernewway.usecase.getcomments.IGetCommentsUseCase.GetCommentsResult

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