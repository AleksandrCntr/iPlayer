package com.example.iplayer

import android.app.Activity
import android.app.Application
import com.example.iplayer.dagger.AppModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initDagger(this).inject(this)
    }

    private fun initDagger(app: App) =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()

    override fun activityInjector() = activityInjector
}