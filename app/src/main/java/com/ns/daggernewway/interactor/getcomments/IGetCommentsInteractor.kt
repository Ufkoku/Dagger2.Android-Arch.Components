package com.ns.daggernewway.interactor.getcomments

import com.ns.daggernewway.entity.rest.Comment

interface IGetCommentsInteractor {

    suspend fun getComments(postId: Int): List<Comment>

}