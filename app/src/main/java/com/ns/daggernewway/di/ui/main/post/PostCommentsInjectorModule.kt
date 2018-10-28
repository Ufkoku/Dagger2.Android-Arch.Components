package com.ns.daggernewway.di.ui.main.post

import com.ns.daggernewway.di.common.scopes.FragmentScope
import com.ns.daggernewway.di.interactor.GetCommentsInteractorModule
import com.ns.daggernewway.ui.main.post.PostCommentsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PostCommentsInjectorModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [PostCommentsModule::class, GetCommentsInteractorModule::class])
    abstract fun postDetailsFragment(): PostCommentsFragment

}
