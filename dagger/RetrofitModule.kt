package com.example.iplayer.dagger

import com.example.iplayer.AppConstants
import com.example.iplayer.network.ITunesApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton


@Module
class RetrofitModule {
    companion object {
        private const val ITUNES_BASE_URL_NAME = "ITUNES_BASE_URL"
    }

    @Provides
    @Named(ITUNES_BASE_URL_NAME)
    fun provideBaseApi() = AppConstants.ITUNES_BASE_URL

    @Provides
    fun provideMoshi() =
        Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi, @Named(ITUNES_BASE_URL_NAME) baseUrl: String) =
        Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideITunesApi(retrofit: Retrofit) =
        retrofit.create(ITunesApi::class.java)
}