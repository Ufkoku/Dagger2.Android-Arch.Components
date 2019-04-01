package com.ufkoku.daggernewway.di.ui.main.feed

import com.ufkoku.daggernewway.di.common.scopes.FragmentScope
import com.ufkoku.daggernewway.ui.main.feed.FeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FeedFragmentInjectorModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [FeedFragmentModule::class])
    abstract fun feedFragment(): FeedFragment

}