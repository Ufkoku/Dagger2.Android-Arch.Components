package com.ufkoku.archcomponents.testable.di.activity.fragmentholder

import com.ufkoku.archcomponents.testable.TestableFragmentHolderActivity
import com.ufkoku.archcomponents.testable.di.fragment.testable.TestableFragmentInjector
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestableFragmentHolderActivityInjector {

    @ContributesAndroidInjector(modules = [TestableFragmentInjector::class])
    abstract fun testableFragmentHolderActivity(): TestableFragmentHolderActivity

}