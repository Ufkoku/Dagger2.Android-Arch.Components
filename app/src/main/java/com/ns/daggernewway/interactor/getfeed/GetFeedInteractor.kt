package com.ns.daggernewway.interactor.getfeed

import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.interactor.getfeed.IGetFeedInteractor.GetFeedResult
import com.ns.daggernewway.rest.NetworkApi

class GetFeedInteractor(private val networkApi: NetworkApi) : IGetFeedInteractor {

    override suspend fun getFeed(): GetFeedResult {
        val posts = try {
            networkApi.getPosts().await()
        } catch (ex: Throwable) {
            return GetFeedResult(false, null)
        }

        val fullPosts = posts.mapNotNull { post ->
            try {
                val user = networkApi.getUser(post.userId).await()
                FullPost(post.id, user, post.title, post.body)
            } catch (ex: Exception) {
                null
            }
        }

        return GetFeedResult(true, fullPosts)
    }

}