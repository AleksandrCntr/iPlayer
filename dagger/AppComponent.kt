package com.example.iplayer.dagger

import com.example.iplayer.ui.home.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    RetrofitModule::class,
    ReposModule::class
])
interface AppComponent {
    fun inject(homeActivity: HomeActivity)
}