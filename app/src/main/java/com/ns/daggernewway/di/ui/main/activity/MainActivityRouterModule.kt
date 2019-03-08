package com.ns.daggernewway.di.ui.main.activity

import com.ns.daggernewway.di.common.scopes.ActivityScope
import com.ns.daggernewway.ui.main.activity.MainActivity
import com.ns.daggernewway.ui.main.activity.router.IMainActivityRouter
import com.ns.daggernewway.ui.main.activity.router.MainActivityRouter
import com.ns.daggernewway.ui.main.feed.FeedFragment
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [MainActivityRouterModule.SubNavigationBinder::class])
class MainActivityRouterModule {

    @Provides
    @ActivityScope
    fun provideMainRouter(activity: MainActivity): IMainActivityRouter =
            MainActivityRouter(activity)

    @Module
    abstract class SubNavigationBinder {

        @Binds
        abstract fun bindFeedFragmentRouter(activityRouter: IMainActivityRouter): FeedFragment.Router

    }

}