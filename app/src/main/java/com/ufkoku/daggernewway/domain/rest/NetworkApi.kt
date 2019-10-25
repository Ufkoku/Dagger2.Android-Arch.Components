package com.ufkoku.daggernewway.domain.rest

import com.ufkoku.daggernewway.domain.rest.entity.RestComment
import com.ufkoku.daggernewway.domain.rest.entity.RestPost
import com.ufkoku.daggernewway.domain.rest.entity.RestUser
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

    @GET("/posts")
    suspend fun getPosts(): List<RestPost>

    @GET("/users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): RestUser

    @GET("/posts/{postId}/comments")
    suspend fun getPostComments(@Path("postId") postId: Int): List<RestComment>

}