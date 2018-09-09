package com.ufkoku.archcomponents.testable.di

import com.ufkoku.archcomponents.testable.di.activity.fragmentholder.TestableFragmentHolderActivityInjector
import com.ufkoku.archcomponents.testable.di.activity.testable.TestableActivityInjector
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

@Component(modules = [
    AndroidSupportInjectionModule::class,
    TestableActivityInjector::class,
    TestableFragmentHolderActivityInjector::class])
interface AppComponent : AndroidInjector<DaggerApplication>