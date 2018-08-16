package com.ns.daggernewway.di.app.beans

import com.ns.daggernewway.App
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

@Module
class OkHttpClientModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(cache)
                .build()
    }

    @Provides
    @Reusable
    fun provideCache(app: App): Cache {
        val cache = File(app.cacheDir, "REST_CACHE")
        return Cache(cache, 50 * 1024 * 1024)
    }

}