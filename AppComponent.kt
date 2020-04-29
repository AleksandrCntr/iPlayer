package com.example.iplayer

import com.example.iplayer.dagger.*
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    UiModule::class,
    NetworkModule::class,
    RetrofitModule::class,
    ReposModule::class,
    RoomModule::class,
    AppModule::class
])
interface AppComponent {

    fun inject(app: App)
}