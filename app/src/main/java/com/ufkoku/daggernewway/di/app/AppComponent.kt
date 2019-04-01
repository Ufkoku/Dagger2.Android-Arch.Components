package com.ufkoku.daggernewway.di.app

import com.ufkoku.daggernewway.di.app.beans.AppModule
import com.ufkoku.daggernewway.di.app.beans.OkHttpClientModule
import com.ufkoku.daggernewway.di.app.beans.RetrofitModule
import com.ufkoku.daggernewway.di.ui.main.activity.MainActivityInjectorModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    OkHttpClientModule::class,
    RetrofitModule::class,
    MainActivityInjectorModule::class])
interface AppComponent : AndroidInjector<DaggerApplication>