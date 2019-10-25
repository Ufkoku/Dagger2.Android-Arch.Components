package com.ufkoku.daggernewway.usecase.getfeed

import com.ufkoku.daggernewway.domain.mapper.post.IPostMapper
import com.ufkoku.daggernewway.domain.rest.NetworkApi
import com.ufkoku.daggernewway.usecase.getfeed.IGetFeedUseCase.GetFeedResult
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetFeedUseCase(private val networkApi: NetworkApi,
                     private val postMapper: IPostMapper) : IGetFeedUseCase {

    override suspend fun getFeed(): GetFeedResult = coroutineScope {
        val posts = runCatching { networkApi.getPosts() }.let {
            if (it.isSuccess) it.getOrNull()!!
            else return@coroutineScope GetFeedResult.Failed
        }

        val fullPosts = posts.associateWith { post -> async { runCatching { networkApi.getUser(post.userId) } } }
                .filter { it.value.await().isSuccess }
                .map {
                    val post = it.key
                    val user = it.value.await().getOrThrow()
                    postMapper.mapRestPost(post, user)
                }

        GetFeedResult.Success(fullPosts)
    }

}