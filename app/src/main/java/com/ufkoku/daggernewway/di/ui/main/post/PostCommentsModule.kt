package com.ufkoku.daggernewway.di.ui.main.post

import androidx.lifecycle.ViewModelProviders
import com.ufkoku.daggernewway.di.common.scopes.FragmentScope
import com.ufkoku.daggernewway.di.usecase.GetCommentsUseCaseModule
import com.ufkoku.daggernewway.ui.main.post.PostCommentsFragment
import com.ufkoku.daggernewway.ui.main.post.viewmodel.CommentsViewModel
import com.ufkoku.daggernewway.ui.main.post.viewmodel.CommentsViewModelFactory
import com.ufkoku.daggernewway.ui.main.post.viewmodel.ICommentsViewModel
import com.ufkoku.daggernewway.usecase.getcomments.IGetCommentsUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [GetCommentsUseCaseModule::class])
class PostCommentsModule {

    @Provides
    @FragmentScope
    fun postDetailsViewModel(fragment: PostCommentsFragment,
                             factory: CommentsViewModelFactory): ICommentsViewModel {
        return ViewModelProviders.of(fragment, factory).get(CommentsViewModel::class.java)
    }

    @Provides
    @FragmentScope
    fun providePostCommentsViewModelFactory(fragment: PostCommentsFragment,
                                            useCase: IGetCommentsUseCase): CommentsViewModelFactory {
        return CommentsViewModelFactory(fragment, fragment.getPostFromArgs(), useCase)
    }

}
