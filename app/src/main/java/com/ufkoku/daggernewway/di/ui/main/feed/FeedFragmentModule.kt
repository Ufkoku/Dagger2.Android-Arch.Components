package com.ufkoku.daggernewway.di.ui.main.feed

import androidx.lifecycle.ViewModelProviders
import com.ufkoku.daggernewway.di.common.scopes.FragmentScope
import com.ufkoku.daggernewway.di.usecase.GetFeedUseCaseModule
import com.ufkoku.daggernewway.ui.main.feed.FeedFragment
import com.ufkoku.daggernewway.ui.main.feed.viewmodel.FeedViewModel
import com.ufkoku.daggernewway.ui.main.feed.viewmodel.FeedViewModelFactory
import com.ufkoku.daggernewway.ui.main.feed.viewmodel.IFeedViewModel
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
