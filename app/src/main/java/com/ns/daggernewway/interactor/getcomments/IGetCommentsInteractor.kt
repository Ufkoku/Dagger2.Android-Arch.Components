package com.ns.daggernewway.interactor.getcomments

import com.ns.daggernewway.entity.rest.Comment
import io.reactivex.Single

interface IGetCommentsInteractor {

    fun getComments(postId: Int): Single<List<Comment>>

}