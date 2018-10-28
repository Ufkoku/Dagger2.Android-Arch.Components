package com.ns.daggernewway.di.ui.main.feed

import com.ns.daggernewway.di.common.scopes.FragmentScope
import com.ns.daggernewway.di.interactor.GetFeedInteractorModule
import com.ns.daggernewway.ui.main.feed.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FeedFragmentInjectorModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [FeedFragmentModule::class, GetFeedInteractorModule::class])
    abstract fun feedFragment(): FeedFragment

}