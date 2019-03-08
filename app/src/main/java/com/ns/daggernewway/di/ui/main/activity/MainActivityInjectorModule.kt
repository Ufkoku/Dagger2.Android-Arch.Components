package com.ns.daggernewway.di.ui.main.activity

import com.ns.daggernewway.di.common.scopes.ActivityScope
import com.ns.daggernewway.di.ui.main.feed.FeedFragmentInjectorModule
import com.ns.daggernewway.di.ui.main.post.PostCommentsInjectorModule
import com.ns.daggernewway.ui.main.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityInjectorModule {

    @ActivityScope
    @ContributesAndroidInjector(
            modules = [
                MainActivityRouterModule::class,
                FeedFragmentInjectorModule::class,
                PostCommentsInjectorModule::class])
    abstract fun mainActivity(): MainActivity

}