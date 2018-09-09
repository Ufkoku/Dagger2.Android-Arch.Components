package com.ufkoku.archcomponents.testable.di.fragment.testable

import com.ufkoku.archcomponents.testable.TestableFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestableFragmentInjector {

    @ContributesAndroidInjector(modules = [TestableFragmentModule::class])
    abstract fun testableFragment(): TestableFragment

}