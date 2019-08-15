package com.ufkoku.daggernewway.di.ui.main.post

import androidx.lifecycle.ViewModelProviders
import com.ufkoku.archcomponents.DaggerSavableViewModel
import com.ufkoku.daggernewway.di.common.scopes.FragmentScope
import com.ufkoku.daggernewway.ui.main.post.PostCommentsFragment
import com.ufkoku.daggernewway.ui.main.post.viewmodel.CommentsViewModel
import com.ufkoku.daggernewway.ui.main.post.viewmodel.ICommentsViewModel
import dagger.Module
import dagger.Provides

@Module
class PostCommentsFragmentModule {

    @Provides
    @FragmentScope
    fun postDetailsViewModel(fragment: PostCommentsFragment,
                             factory: DaggerSavableViewModel.Factory): ICommentsViewModel {
        return ViewModelProviders.of(fragment, factory).get(CommentsViewModel::class.java)
    }

    @Provides
    @FragmentScope
    fun providePostCommentsViewModelFactory(fragment: PostCommentsFragment): DaggerSavableViewModel.Factory {
        return DaggerSavableViewModel.Factory(fragment, fragment, CommentsViewModel.buildDefaultValues(fragment.getPostFromArgs()))
    }

}
