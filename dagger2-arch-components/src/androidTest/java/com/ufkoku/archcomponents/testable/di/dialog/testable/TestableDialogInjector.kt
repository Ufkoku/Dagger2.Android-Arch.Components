package com.ufkoku.archcomponents.testable.di.dialog.testable

import com.ufkoku.archcomponents.testable.TestableDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestableDialogInjector {

    @ContributesAndroidInjector(modules = [TestableDialogModule::class])
    abstract fun testableDialog(): TestableDialog

}