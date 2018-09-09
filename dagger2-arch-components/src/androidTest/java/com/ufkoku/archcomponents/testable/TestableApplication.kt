package com.ufkoku.archcomponents.testable

import com.ufkoku.archcomponents.R
import com.ufkoku.archcomponents.testable.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class TestableApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_AppCompat)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.create()
    }

}