package com.ufkoku.daggernewway.di.app.beans

import com.ufkoku.daggernewway.App
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Singleton

@Module
class OkHttpClientModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache,
                            loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Reusable
    fun provideCache(app: App): Cache {
        val cache = File(app.cacheDir, "REST_CACHE")
        return Cache(cache, 50 * 1024 * 1024)
    }

    @Provides
    @Reusable
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

}