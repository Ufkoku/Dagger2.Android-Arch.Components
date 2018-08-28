package com.ns.daggernewway.rest

import com.ns.daggernewway.entity.rest.Comment
import com.ns.daggernewway.entity.rest.Post
import com.ns.daggernewway.entity.rest.User
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

    @GET("/posts")
    fun getPosts(): Deferred<List<Post>>

    @GET("/users/{userId}")
    fun getUser(@Path("userId") userId: Int): Deferred<User>

    @GET("/posts/{postId}/comments")
    fun getPostComments(@Path("postId") postId: Int): Deferred<List<Comment>>

}