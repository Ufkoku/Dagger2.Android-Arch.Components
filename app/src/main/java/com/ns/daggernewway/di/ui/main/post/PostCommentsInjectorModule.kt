package com.ns.daggernewway.di.ui.main.post

import com.ns.daggernewway.di.common.scopes.FragmentScope
import com.ns.daggernewway.ui.main.post.PostCommentsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PostCommentsInjectorModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [PostCommentsModule::class])
    abstract fun postCommentsFragment(): PostCommentsFragment

}
