package com.ns.daggernewway.interactor.getcomments

import com.ns.daggernewway.entity.rest.Comment

interface IGetCommentsInteractor {

    suspend fun getComments(postId: Int): GetCommentsResult

    data class GetCommentsResult(val isSuccess: Boolean,
                                 val data: List<Comment>?)

}