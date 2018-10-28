package com.ns.daggernewway.interactor.getcomments

import android.util.Log
import com.ns.daggernewway.interactor.getcomments.IGetCommentsInteractor.GetCommentsResult
import com.ns.daggernewway.rest.NetworkApi

private const val TAG = "GetCommentsInteractor"

class GetCommentsInteractor(private val networkApi: NetworkApi) : IGetCommentsInteractor {

    override suspend fun getComments(postId: Int): GetCommentsResult {
        return try {
            val data = networkApi.getPostComments(postId).await()
            GetCommentsResult(true, data)
        } catch (ex: Throwable) {
            Log.e(TAG, "Get comments request failed", ex)
            GetCommentsResult(false, null)
        }
    }

}