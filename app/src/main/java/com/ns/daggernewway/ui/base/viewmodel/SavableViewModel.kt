package com.ns.daggernewway.ui.base.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.ns.daggernewway.interactor.getcomments.IGetCommentsInteractor
import com.ns.daggernewway.ui.utils.viewmodel.ownes

abstract class SavableViewModel(app: Application) : AndroidViewModel(app) {

    constructor(app: Application,
                owner: ViewModelStoreOwner,
                savedInstanceState: Bundle) : this(app) {
        restore(owner, savedInstanceState)
    }

    fun save(caller: ViewModelStoreOwner, bundle: Bundle) {
        val key = caller.ownes(this)
        if (key != null) {
            val innerBundle = Bundle()
            save(innerBundle)
            bundle.putBundle(key, innerBundle)
        } else {
            throw IllegalAccessException("Caller $caller is not owner of $this")
        }
    }

    private fun restore(caller: ViewModelStoreOwner, bundle: Bundle) {
        val key = caller.ownes(this)
        if (key != null) {
            val innerBundle = bundle.getBundle(key)
            restore(innerBundle!!)
        } else {
            throw IllegalAccessException("Caller $caller is not owner of $this")
        }
    }

    protected abstract fun save(bundle: Bundle)

    protected abstract fun restore(bundle: Bundle)

}