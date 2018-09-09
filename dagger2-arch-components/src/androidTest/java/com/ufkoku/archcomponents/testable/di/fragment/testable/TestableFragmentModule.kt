package com.ufkoku.archcomponents.testable.di.fragment.testable

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ufkoku.archcomponents.testable.TestableFragment
import com.ufkoku.archcomponents.testable.TestableFragment.ArgumentExtractorModule
import com.ufkoku.archcomponents.testable.TestableFragment.ViewModelDefault
import com.ufkoku.archcomponents.testable.TestableViewModel
import dagger.Module
import dagger.Provides
import java.lang.IllegalArgumentException
import javax.inject.Named

@Module(includes = [ArgumentExtractorModule::class])
class TestableFragmentModule {

    @Provides
    fun viewModelSavable(fragment: TestableFragment,
                         factory: SavableViewModelFactory): TestableViewModel {
        return ViewModelProviders.of(fragment, factory).get(TestableViewModel::class.java)
    }

    @Provides
    fun viewModelDefault(fragment: TestableFragment): ViewModelDefault {
        return ViewModelProviders.of(fragment).get(ViewModelDefault::class.java)
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