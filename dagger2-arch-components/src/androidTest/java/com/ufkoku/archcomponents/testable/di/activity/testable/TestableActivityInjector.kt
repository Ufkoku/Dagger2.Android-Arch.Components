package com.ufkoku.archcomponents.testable.di.activity.testable

import com.ufkoku.archcomponents.testable.TestableActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestableActivityInjector {

    @ContributesAndroidInjector(modules = [TestableActivityModule::class])
    abstract fun testableActivity(): TestableActivity

}