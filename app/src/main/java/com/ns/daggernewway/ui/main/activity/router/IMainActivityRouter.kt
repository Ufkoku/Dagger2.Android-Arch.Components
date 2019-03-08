package com.ns.daggernewway.ui.main.activity.router

import com.ns.daggernewway.ui.main.feed.FeedFragment

interface IMainActivityRouter: FeedFragment.Router {

    fun moveToStart()

}