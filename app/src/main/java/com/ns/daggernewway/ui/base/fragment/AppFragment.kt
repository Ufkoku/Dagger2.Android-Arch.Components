package com.ns.daggernewway.ui.base.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ns.daggernewway.ui.base.viewmodel.SavableViewModel
import com.ns.daggernewway.ui.utils.viewmodel.getAttachedViewModels
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class AppFragment : Fragment(), HasSupportFragmentInjector {

    protected var savedInstanceState: Bundle? = null
        private set
        get() {
            return if (field == null) {
                null
            } else {
                return Bundle(field)
            }
        }

    @Inject
    protected lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState

        AndroidSupportInjection.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        getAttachedViewModels().filter { it.value is SavableViewModel }
                .forEach { (it.value as SavableViewModel).save(outState) }

        super.onSaveInstanceState(outState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

}