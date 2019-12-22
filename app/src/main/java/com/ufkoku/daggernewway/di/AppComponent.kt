package com.ufkoku.daggernewway.di

import com.ufkoku.daggernewway.di.domain.MapperModule
import com.ufkoku.daggernewway.di.domain.OkHttpClientModule
import com.ufkoku.daggernewway.di.domain.RetrofitModule
import com.ufkoku.daggernewway.di.ui.main.activity.MainActivityInjectorModule
import com.ufkoku.daggernewway.di.usecase.UseCaseModule
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
    MapperModule::class,
    UseCaseModule::class,

    MainActivityInjectorModule::class])
interface AppComponent : AndroidInjector<DaggerApplication>