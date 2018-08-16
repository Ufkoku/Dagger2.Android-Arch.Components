package com.ns.daggernewway.ui.base.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel

abstract class SavableViewModel(app: Application) : AndroidViewModel(app) {

    constructor(app: Application,
                savedInstanceState: Bundle) : this(app) {
        restore(savedInstanceState)
    }

    fun save(bundle: Bundle) {
        val innerBundle = Bundle()
        saveInner(innerBundle)
        bundle.putBundle(javaClass.canonicalName, innerBundle)
    }

    private fun restore(bundle: Bundle) {
        val innerBundle = bundle.getBundle(javaClass.canonicalName)
        restoreInner(innerBundle!!)
    }

    protected abstract fun saveInner(bundle: Bundle)

    protected abstract fun restoreInner(bundle: Bundle)

}