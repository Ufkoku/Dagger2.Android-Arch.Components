package com.ns.daggernewway.interactor.getcomments

import com.ns.daggernewway.entity.rest.Comment
import com.ns.daggernewway.rest.NetworkApi

class GetCommentsInteractor(private val networkApi: NetworkApi) : IGetCommentsInteractor {

    override suspend fun getComments(postId: Int): List<Comment> {
        return networkApi.getPostComments(postId).await()
    }

}