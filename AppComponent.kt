package com.example.iplayer

import com.example.iplayer.dagger.NetworkModule
import com.example.iplayer.dagger.ReposModule
import com.example.iplayer.dagger.RetrofitModule
import com.example.iplayer.dagger.UiModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    UiModule::class,
    NetworkModule::class,
    RetrofitModule::class,
    ReposModule::class
])
interface AppComponent {

    fun inject(app: App)

}