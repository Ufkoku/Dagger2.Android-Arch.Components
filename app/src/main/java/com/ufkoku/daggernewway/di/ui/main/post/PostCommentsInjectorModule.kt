package com.ufkoku.daggernewway.di.ui.main.post

import com.ufkoku.daggernewway.di.common.scopes.FragmentScope
import com.ufkoku.daggernewway.ui.main.post.PostCommentsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PostCommentsInjectorModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [PostCommentsFragmentModule::class])
    abstract fun postCommentsFragment(): PostCommentsFragment

}
