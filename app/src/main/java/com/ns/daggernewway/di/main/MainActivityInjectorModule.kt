package com.ns.daggernewway.di.main

import com.ns.daggernewway.di.common.scopes.ActivityScope
import com.ns.daggernewway.di.feed.FeedFragmentInjectorModule
import com.ns.daggernewway.di.postcomments.PostCommentsInjectorModule
import com.ns.daggernewway.ui.main.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityInjectorModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FeedFragmentInjectorModule::class, PostCommentsInjectorModule::class])
    abstract fun mainActivity(): MainActivity

}