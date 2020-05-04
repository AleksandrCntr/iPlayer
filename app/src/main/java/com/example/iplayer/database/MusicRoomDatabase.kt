package com.example.iplayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.iplayer.database.dao.AlbumDao

@Database(entities = [Album::class], version = 1, exportSchema = false)
@TypeConverters(TimeConverter::class)
abstract class MusicRoomDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    companion object {

        private var INSTANCE: MusicRoomDatabase? = null

        fun getDatabase(context: Context): MusicRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicRoomDatabase::class.java,
                    "music_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}