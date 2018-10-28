package com.ufkoku.archcomponents.testable

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.ufkoku.archcomponents.DaggerArchDialog
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

class TestableDialog : DaggerArchDialog() {

    companion object {

        private const val KEY_DATA = "keyData"

        fun buildFragment(data: String): TestableDialog {
            val fragment = TestableDialog()

            val args = Bundle()
            args.putString(KEY_DATA, data)

            fragment.arguments = args

            return fragment
        }

    }

    @Inject
    lateinit var viewModelSavable: TestableViewModel

    @Inject
    lateinit var viewModelDefault: ViewModelDefault

    val isSavedInstanceInitialized: Boolean
        get() {
            return savedInstanceState != null
        }

    class ViewModelDefault : ViewModel()

    @Module
    class ArgumentExtractorModule {

        companion object {
            const val QUALIFIER = "TestableDialog.ArgumentExtractorModule"
        }

        @Provides
        @Named(QUALIFIER)
        fun provideData(fragment: TestableDialog): String? {
            return if (fragment.savedInstanceState == null)
                fragment.arguments?.getString(KEY_DATA)
            else null
        }

        @Provides
        @Named(QUALIFIER)
        fun provideSavedInstance(fragment: TestableDialog): Bundle? {
            return fragment.savedInstanceState
        }

    }

}