package com.ns.daggernewway.usecase.getcomments

import com.ns.daggernewway.domain.ui.entity.Comment

interface IGetCommentsUseCase {

    suspend fun getComments(postId: Int): GetCommentsResult

    sealed class GetCommentsResult {
        class Success(val data: List<Comment>) : GetCommentsResult()
        object Failed : GetCommentsResult()
    }

}