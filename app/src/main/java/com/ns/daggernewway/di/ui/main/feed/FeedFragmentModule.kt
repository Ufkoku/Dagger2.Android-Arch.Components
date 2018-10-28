package com.ns.daggernewway.di.ui.main.feed

import androidx.lifecycle.ViewModelProviders
import com.ns.daggernewway.di.common.scopes.FragmentScope
import com.ns.daggernewway.ui.common.viewmodel.getfeed.FeedViewModel
import com.ns.daggernewway.ui.common.viewmodel.getfeed.FeedViewModelFactory
import com.ns.daggernewway.ui.main.feed.FeedFragment
import dagger.Module
import dagger.Provides

@Module
class FeedFragmentModule {

    @Provides
    @FragmentScope
    fun provideFeedViewModel(fragment: FeedFragment,
                             factory: FeedViewModelFactory): FeedViewModel {
        return ViewModelProviders.of(fragment, factory).get(FeedViewModel::class.java)
    }

}
