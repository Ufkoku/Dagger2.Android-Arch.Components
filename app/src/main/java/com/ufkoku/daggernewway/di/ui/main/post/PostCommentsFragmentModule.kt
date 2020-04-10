package com.ufkoku.daggernewway.di.ui.main.post

import androidx.lifecycle.ViewModelProviders
import com.ufkoku.daggernewway.di.utils.FragmentScope
import com.ufkoku.daggernewway.ui.main.post.PostCommentsFragment
import com.ufkoku.daggernewway.ui.main.post.viewmodel.CommentsViewModel
import com.ufkoku.daggernewway.ui.main.post.viewmodel.CommentsViewModelImpl
import com.ufkoku.daggernewway.ui.main.post.viewmodel.CommentsViewModelImplFactory
import com.ufkoku.daggernewway.usecase.GetCommentsUseCase
import dagger.Module
import dagger.Provides

@Module
class PostCommentsFragmentModule {

    @Provides
    @FragmentScope
    fun postDetailsViewModel(fragment: PostCommentsFragment,
                             factory: CommentsViewModelImplFactory): CommentsViewModel {
        return ViewModelProviders.of(fragment, factory).get(CommentsViewModelImpl::class.java)
    }

    @Provides
    @FragmentScope
    fun providePostCommentsViewModelFactory(fragment: PostCommentsFragment,
                                            useCase: GetCommentsUseCase): CommentsViewModelImplFactory {
        return CommentsViewModelImplFactory(fragment, fragment.getPostFromArgs(), useCase)
    }

}