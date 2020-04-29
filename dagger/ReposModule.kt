package com.example.iplayer.dagger

import com.example.iplayer.database.dao.AlbumDao
import com.example.iplayer.network.ITunesApi
import com.example.iplayer.repositories.ITunesRepository
import com.example.iplayer.repositories.RoomRepository
import dagger.Module
import dagger.Provides

@Module
class ReposModule {

    @Provides
    fun provideITunesRepository(iTunesApi: ITunesApi)
            = ITunesRepository(iTunesApi)

    @Provides
    fun provideRoomRepository(albumDao: AlbumDao)
            = RoomRepository(albumDao)
}