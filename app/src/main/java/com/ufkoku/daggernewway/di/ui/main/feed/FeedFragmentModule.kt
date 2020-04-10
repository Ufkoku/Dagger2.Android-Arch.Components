package com.ufkoku.daggernewway.di.ui.main.feed

import androidx.lifecycle.ViewModelProviders
import com.ufkoku.archcomponents.DaggerViewModel
import com.ufkoku.daggernewway.di.utils.FragmentScope
import com.ufkoku.daggernewway.ui.main.feed.FeedFragment
import com.ufkoku.daggernewway.ui.main.feed.viewmodel.FeedViewModel
import com.ufkoku.daggernewway.ui.main.feed.viewmodel.FeedViewModelImpl
import dagger.Module
import dagger.Provides

@Module
class FeedFragmentModule {

    @Provides
    @FragmentScope
    fun provideFeedViewModel(fragment: FeedFragment,
                             factory: DaggerViewModel.Factory): FeedViewModel {
        return ViewModelProviders.of(fragment, factory).get(FeedViewModelImpl::class.java)
    }

    @Provides
    @FragmentScope
    fun provideFeedViewModelFactory(fragment: FeedFragment): DaggerViewModel.Factory {
        return DaggerViewModel.Factory(fragment)
    }

}
