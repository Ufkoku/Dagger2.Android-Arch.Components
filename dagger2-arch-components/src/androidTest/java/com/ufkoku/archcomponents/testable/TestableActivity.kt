package com.ufkoku.archcomponents.testable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.ufkoku.archcomponents.DaggerArchActivity
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

class TestableActivity : DaggerArchActivity() {

    companion object {

        private const val EXTRA_DATA = "extraData"

        fun buildIntent(context: Context, data: String): Intent {
            val intent = Intent(context, TestableActivity::class.java)

            intent.putExtra(EXTRA_DATA, data)

            return intent
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
            const val QUALIFIER = "TestableActivity.ArgumentExtractorModule"
        }

        @Provides
        @Named(QUALIFIER)
        fun provideData(activity: TestableActivity): String? {
            return if (activity.savedInstanceState == null)
                activity.intent.getStringExtra(EXTRA_DATA)
            else null
        }

        @Provides
        @Named(QUALIFIER)
        fun provideSavedInstance(activity: TestableActivity): Bundle? {
            return activity.savedInstanceState
        }

    }
}