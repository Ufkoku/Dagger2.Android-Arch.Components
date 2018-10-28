package com.ufkoku.archcomponents.testable.di.dialog.testable

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ufkoku.archcomponents.testable.TestableDialog
import com.ufkoku.archcomponents.testable.TestableDialog.ArgumentExtractorModule
import com.ufkoku.archcomponents.testable.TestableViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [ArgumentExtractorModule::class])
class TestableDialogModule {

    @Provides
    fun viewModelSavable(fragment: TestableDialog,
                         factory: SavableViewModelFactory): TestableViewModel {
        return ViewModelProviders.of(fragment, factory).get(TestableViewModel::class.java)
    }

    @Provides
    fun viewModelDefault(fragment: TestableDialog): TestableDialog.ViewModelDefault {
        return ViewModelProviders.of(fragment).get(TestableDialog.ViewModelDefault::class.java)
    }

    @Provides
    fun savableViewModelFactory(@Named(ArgumentExtractorModule.QUALIFIER) data: String?,
                                @Named(ArgumentExtractorModule.QUALIFIER) savedInstance: Bundle?): SavableViewModelFactory {
        return SavableViewModelFactory(data, savedInstance)
    }

    class SavableViewModelFactory(private val data: String?,
                                  private val savedInstance: Bundle?) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return (when {
                savedInstance != null -> TestableViewModel(savedInstance = savedInstance)
                data != null -> TestableViewModel(data = data)
                else -> throw IllegalArgumentException("Cannot create ViewModel")
            }) as T
        }

    }

}