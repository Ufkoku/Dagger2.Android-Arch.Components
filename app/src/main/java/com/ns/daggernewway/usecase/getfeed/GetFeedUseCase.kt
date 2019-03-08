package com.ns.daggernewway.usecase.getfeed

import com.ns.daggernewway.domain.mapper.post.IPostMapper
import com.ns.daggernewway.domain.rest.NetworkApi
import com.ns.daggernewway.usecase.getfeed.IGetFeedUseCase.GetFeedResult

class GetFeedUseCase(private val networkApi: NetworkApi,
                     private val postMapper: IPostMapper) : IGetFeedUseCase {

    override suspend fun getFeed(): GetFeedResult {
        val posts = try {
            networkApi.getPosts().await()
        } catch (ex: Throwable) {
            return GetFeedResult.Failed
        }

        val fullPosts = posts.mapNotNull { post ->
            try {
                val user = networkApi.getUser(post.userId).await()
                postMapper.mapRestPost(post, user)
            } catch (ex: Exception) {
                null
            }
        }

        return GetFeedResult.Success(fullPosts)
    }

}