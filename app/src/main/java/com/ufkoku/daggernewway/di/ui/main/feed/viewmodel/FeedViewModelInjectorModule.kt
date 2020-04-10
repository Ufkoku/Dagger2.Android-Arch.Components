package com.ufkoku.daggernewway.di.ui.main.feed.viewmodel

import com.ufkoku.daggernewway.ui.main.feed.viewmodel.FeedViewModelImpl
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FeedViewModelInjectorModule {

    @ContributesAndroidInjector
    abstract fun feedViewModel(): FeedViewModelImpl

}
