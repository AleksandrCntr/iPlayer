package com.example.iplayer

import android.app.Application
import com.example.iplayer.dagger.AppComponent
import com.example.iplayer.dagger.DaggerAppComponent

class App : Application() {

    lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}