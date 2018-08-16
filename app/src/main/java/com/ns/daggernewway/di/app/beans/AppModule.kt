package com.ns.daggernewway.di.app.beans

import com.ns.daggernewway.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: App) {

    @Provides
    fun provideApp() = app

}
