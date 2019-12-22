package com.ufkoku.daggernewway.di.ui.main.activity

import com.ufkoku.daggernewway.di.ui.main.feed.FeedFragmentInjectorModule
import com.ufkoku.daggernewway.di.ui.main.post.PostCommentsInjectorModule
import com.ufkoku.daggernewway.di.utils.ActivityScope
import com.ufkoku.daggernewway.ui.main.activity.MainActivity
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