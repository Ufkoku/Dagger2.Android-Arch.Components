package com.ns.daggernewway.rest

import com.ns.daggernewway.entity.rest.Comment
import com.ns.daggernewway.entity.rest.Post
import com.ns.daggernewway.entity.rest.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

    @GET("/posts")
    fun getPosts(): Single<List<Post>>

    @GET("/users/{userId}")
    fun getUser(@Path("userId") userId: Int): Single<User>

    @GET("/posts/{postId}/comments")
    fun getPostComments(@Path("postId") postId: Int): Single<List<Comment>>

}