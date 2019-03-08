package com.ns.daggernewway.di.ui.main.feed

import androidx.lifecycle.ViewModelProviders
import com.ns.daggernewway.di.common.scopes.FragmentScope
import com.ns.daggernewway.di.usecase.GetFeedUseCaseModule
import com.ns.daggernewway.ui.main.feed.FeedFragment
import com.ns.daggernewway.ui.main.feed.viewmodel.FeedViewModel
import com.ns.daggernewway.ui.main.feed.viewmodel.FeedViewModelFactory
import com.ns.daggernewway.ui.main.feed.viewmodel.IFeedViewModel
import dagger.Module
import dagger.Provides

@Module(includes = [GetFeedUseCaseModule::class])
class FeedFragmentModule {

    @Provides
    @FragmentScope
    fun provideFeedViewModel(fragment: FeedFragment,
                             factory: FeedViewModelFactory): IFeedViewModel {
        return ViewModelProviders.of(fragment, factory).get(FeedViewModel::class.java)
    }

}
