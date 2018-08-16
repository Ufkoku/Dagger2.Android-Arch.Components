package com.ns.daggernewway.interactor.getfeed

import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.rest.NetworkApi
import io.reactivex.Observable
import io.reactivex.Single

class GetFeedInteractor(private val networkApi: NetworkApi) : IGetFeedInteractor {

    override fun getFeed(): Single<out List<FullPost>> {
        return networkApi.getPosts()
                .flatMapObservable { Observable.fromIterable(it) }
                .flatMap { post ->
                    networkApi.getUser(post.userId)
                            .map { user -> FullPost(post.id, user, post.title, post.body) }
                            .toObservable()
                            .onErrorResumeNext { _: Throwable -> Observable.empty<FullPost>() }
                }
                .collectInto(ArrayList()) { list, post -> list.add(post) }
    }

}