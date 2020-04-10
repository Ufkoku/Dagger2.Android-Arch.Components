package com.ufkoku.daggernewway.usecase

import com.ufkoku.daggernewway.domain.mapper.PostMapper
import com.ufkoku.daggernewway.domain.rest.NetworkApi
import com.ufkoku.daggernewway.domain.ui.entity.Post
import com.ufkoku.daggernewway.usecase.GetFeedUseCase.GetFeedResult
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

interface GetFeedUseCase {

    suspend fun getFeed(): GetFeedResult

    sealed class GetFeedResult {
        class Success(val data: List<Post>) : GetFeedResult()

        object Failed : GetFeedResult()
    }

}

class GetFeedUseCaseImpl(private val networkApi: NetworkApi,
                         private val postMapper: PostMapper) : GetFeedUseCase {

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