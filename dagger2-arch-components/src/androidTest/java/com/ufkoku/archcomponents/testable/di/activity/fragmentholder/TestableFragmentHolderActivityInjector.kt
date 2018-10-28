package com.ufkoku.archcomponents.testable.di.activity.fragmentholder

import com.ufkoku.archcomponents.testable.TestableFragmentHolderActivity
import com.ufkoku.archcomponents.testable.di.dialog.testable.TestableDialogInjector
import com.ufkoku.archcomponents.testable.di.fragment.testable.TestableFragmentInjector
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestableFragmentHolderActivityInjector {

    @ContributesAndroidInjector(modules = [TestableFragmentInjector::class, TestableDialogInjector::class])
    abstract fun testableFragmentHolderActivity(): TestableFragmentHolderActivity

}