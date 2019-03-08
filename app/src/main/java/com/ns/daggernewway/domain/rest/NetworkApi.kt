package com.ns.daggernewway.domain.rest

import com.ns.daggernewway.domain.rest.entity.RestComment
import com.ns.daggernewway.domain.rest.entity.RestPost
import com.ns.daggernewway.domain.rest.entity.RestUser
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

    @GET("/posts")
    fun getPosts(): Deferred<List<RestPost>>

    @GET("/users/{userId}")
    fun getUser(@Path("userId") userId: Int): Deferred<RestUser>

    @GET("/posts/{postId}/comments")
    fun getPostComments(@Path("postId") postId: Int): Deferred<List<RestComment>>

}