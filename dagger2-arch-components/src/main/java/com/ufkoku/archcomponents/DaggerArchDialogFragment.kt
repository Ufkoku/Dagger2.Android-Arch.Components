package com.ufkoku.archcomponents

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class DaggerArchDialogFragment : DialogFragment(), HasSupportFragmentInjector {

    @Inject
    protected lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

}
