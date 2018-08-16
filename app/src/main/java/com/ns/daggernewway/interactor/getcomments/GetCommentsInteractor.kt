package com.ns.daggernewway.interactor.getcomments

import com.ns.daggernewway.entity.rest.Comment
import com.ns.daggernewway.rest.NetworkApi
import io.reactivex.Single

class GetCommentsInteractor(private val networkApi: NetworkApi) : IGetCommentsInteractor {

    override fun getComments(postId: Int): Single<List<Comment>> {
        return networkApi.getPostComments(postId)
    }

}