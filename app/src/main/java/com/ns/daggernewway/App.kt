package com.ns.daggernewway

import com.ns.daggernewway.di.app.DaggerAppComponent
import com.ns.daggernewway.di.app.beans.AppModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}