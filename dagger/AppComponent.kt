package com.example.iplayer.dagger

import com.example.iplayer.ui.home.HomeActivity
import dagger.Component

@Component(modules = [])
interface AppComponent {
    fun inject(homeActivity: HomeActivity)
}