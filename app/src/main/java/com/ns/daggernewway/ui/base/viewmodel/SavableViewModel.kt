package com.ns.daggernewway.ui.base.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel

abstract class SavableViewModel(bundleSuffix: String = "") : ViewModel() {

    /**
     * Allows you to use two models of same instance attached to single component
     * */
    @Suppress("CanBePrimaryConstructorProperty")
    private val bundleSuffix = bundleSuffix

    private val bundleKey: String by lazy {
        val basicKey = javaClass.canonicalName!!
        if (this.bundleSuffix.isEmpty()) {
            basicKey
        } else {
            "$basicKey$${this.bundleSuffix}"
        }

    }

    constructor(savedInstanceState: Bundle,
                bundleSuffix: String = "") : this(bundleSuffix) {
        restore(savedInstanceState)
    }

    fun save(bundle: Bundle) {
        val innerBundle = Bundle()
        saveInner(innerBundle)
        bundle.putBundle(bundleKey, innerBundle)
    }

    private fun restore(bundle: Bundle) {
        val innerBundle = bundle.getBundle(bundleKey)
        restoreInner(innerBundle!!)
    }

    protected abstract fun saveInner(bundle: Bundle)

    protected abstract fun restoreInner(bundle: Bundle)

}