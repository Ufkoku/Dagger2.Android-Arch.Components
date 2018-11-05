package com.ufkoku.archcomponents

import android.os.Bundle
import androidx.lifecycle.ViewModel

private const val DEFAULT_SUFFIX = ""

@Suppress("CanBePrimaryConstructorProperty")
abstract class SavableViewModel(bundleSuffix: String = DEFAULT_SUFFIX) : ViewModel() {

    /**
     * Allows you to use two models of same instance attached to single component
     * */
    private val bundleSuffix: String = bundleSuffix

    private val bundleKey: String by lazy(LazyThreadSafetyMode.NONE) {
        val basicKey = javaClass.simpleName
        if (this.bundleSuffix.isEmpty()) {
            basicKey
        } else {
            "$basicKey$${this.bundleSuffix}"
        }
    }

    constructor(bundleSuffix: String = DEFAULT_SUFFIX, savedInstanceState: Bundle) : this(bundleSuffix) {
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