package com.ufkoku.archcomponents.testable

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.ufkoku.archcomponents.DaggerArchFragment
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

class TestableFragment : DaggerArchFragment() {

    companion object {

        private const val KEY_DATA = "keyData"

        fun buildFragment(data: String): TestableFragment {
            val fragment = TestableFragment()

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
            const val QUALIFIER = "TestableFragment.ArgumentExtractorModule"
        }

        @Provides
        @Named(QUALIFIER)
        fun provideData(fragment: TestableFragment): String? {
            return if (fragment.savedInstanceState == null)
                fragment.arguments?.getString(KEY_DATA)
            else null
        }

        @Provides
        @Named(QUALIFIER)
        fun provideSavedInstance(fragment: TestableFragment): Bundle? {
            return fragment.savedInstanceState
        }

    }

}