package com.ufkoku.daggernewway.ui.main.activity.router

import com.ufkoku.daggernewway.ui.main.feed.FeedFragment

interface IMainActivityRouter: FeedFragment.Router {

    fun moveToStart()

}