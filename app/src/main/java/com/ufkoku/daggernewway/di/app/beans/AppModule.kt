package com.ufkoku.daggernewway.di.app.beans

import com.ufkoku.daggernewway.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: App) {

    @Provides
    fun provideApp() = app

}
