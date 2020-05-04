package com.example.iplayer.dagger

import com.example.iplayer.AppComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface TestAppComponent : AppComponent {
//    fun inject(iTunesApiChannelSearchTest : HomeViewModelChannelSearchTest)
}