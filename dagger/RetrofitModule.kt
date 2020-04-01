package com.example.iplayer.dagger

import com.example.iplayer.AppConstants
import com.example.iplayer.network.ITunesApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
open class RetrofitModule {
    companion object {
        private const val ITUNES_BASE_URL_NAME = "ITUNES_BASE_URL"
    }

    @Provides
    @Named(ITUNES_BASE_URL_NAME)
    fun provideBaseApi() = AppConstants.ITUNES_BASE_URL

    @Provides
    @Singleton
    open fun provideRetrofit(client: OkHttpClient, @Named(ITUNES_BASE_URL_NAME) baseUrl: String) =
        Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    open fun provideITunesApi(retrofit: Retrofit) =
        retrofit.create(ITunesApi::class.java)
}