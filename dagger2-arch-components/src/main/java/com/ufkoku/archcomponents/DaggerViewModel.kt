package com.ufkoku.archcomponents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.HasAndroidInjector

abstract class DaggerViewModel(hasAndroidInjector: HasAndroidInjector) : ViewModel() {

    init {
        @Suppress("LeakingThis")
        hasAndroidInjector.androidInjector().inject(this)
    }

    class Factory(private val hasAndroidInjector: HasAndroidInjector) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = modelClass.constructors.let {
            if (it.size == 1) {
                val ctr = it[0]
                if (ctr.parameterTypes.size == 1
                        && ctr.parameterTypes[0].isAssignableFrom(HasAndroidInjector::class.java)) {
                    return@let ctr.newInstance(hasAndroidInjector) as T
                }
            }
            return@let null
        } ?: throw IllegalArgumentException("Provided ${modelClass.canonicalName} must contain exactly 1 constructor with single argument which is ${HasAndroidInjector::class.java.canonicalName}")

    }

}
