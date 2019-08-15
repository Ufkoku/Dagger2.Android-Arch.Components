package com.ufkoku.archcomponents

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.android.HasAndroidInjector

abstract class DaggerSavableViewModel(hasAndroidInjector: HasAndroidInjector,
                                      protected val savedStateHandle: SavedStateHandle) : DaggerViewModel(hasAndroidInjector) {

    class Factory(private val hasAndroidInjector: HasAndroidInjector,
                  savedRegistryOwner: SavedStateRegistryOwner,
                  defaultArgs: Bundle? = null) : AbstractSavedStateViewModelFactory(savedRegistryOwner, defaultArgs) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T = modelClass.constructors.let {
            if (it.size == 1) {
                val ctr = it[0]
                if (ctr.parameterTypes.size == 2
                        && ctr.parameterTypes[0].isAssignableFrom(HasAndroidInjector::class.java)
                        && ctr.parameterTypes[1].isAssignableFrom(SavedStateHandle::class.java)) {
                    return@let ctr.newInstance(hasAndroidInjector, handle) as T
                }
            }
            return@let null
        } ?: throw IllegalArgumentException("Provided ${modelClass.canonicalName} must contain exactly 1 constructor with 2 arguments which are ${HasAndroidInjector::class.java.canonicalName} and ${SavedStateHandle::class.java.canonicalName}")

    }

}