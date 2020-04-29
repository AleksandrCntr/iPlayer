package com.example.iplayer.dagger

import android.content.Context
import com.example.iplayer.database.MusicRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabaseInstance(app: Context)
            = MusicRoomDatabase.getDatabase(app)

    @Provides
    @Singleton
    fun provideAlbumDao(musicRoomDatabase: MusicRoomDatabase)
            = musicRoomDatabase.albumDao()
}