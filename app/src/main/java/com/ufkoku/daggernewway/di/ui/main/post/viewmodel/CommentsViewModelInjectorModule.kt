package com.ufkoku.daggernewway.di.ui.main.post.viewmodel

import com.ufkoku.daggernewway.di.usecase.GetCommentsUseCaseModule
import com.ufkoku.daggernewway.ui.main.post.viewmodel.CommentsViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CommentsViewModelInjectorModule {

    @ContributesAndroidInjector(modules = [GetCommentsUseCaseModule::class])
    abstract fun commentsViewModel(): CommentsViewModel

}
