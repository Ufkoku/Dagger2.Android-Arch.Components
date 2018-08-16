package com.ns.daggernewway.ui.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ns.daggernewway.ui.base.viewmodel.SavableViewModel
import com.ns.daggernewway.ui.utils.viewmodel.getAttachedViewModels
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class AppActivity : AppCompatActivity(), HasSupportFragmentInjector {

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
    protected lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        getAttachedViewModels().filter { it.value is SavableViewModel }
                .forEach { (it.value as SavableViewModel).save(outState) }

        super.onSaveInstanceState(outState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return supportFragmentInjector
    }

}
