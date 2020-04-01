package com.example.iplayer.dagger

import com.example.iplayer.network.ITunesApi
import com.example.iplayer.repositories.ITunesRepository
import dagger.Module
import dagger.Provides

@Module
class ReposModule {

    @Provides
    fun provideITunesRepository(iTunesApi: ITunesApi)
            = ITunesRepository(iTunesApi)
}