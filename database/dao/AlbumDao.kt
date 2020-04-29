package com.example.iplayer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.iplayer.database.Album

@Dao
interface AlbumDao {

    @Query("SELECT *, max(dateViewed) as latest_viewed FROM album_table GROUP BY collectionId ORDER BY latest_viewed DESC LIMIT 30")
    fun takeLatestThirtyDistinctAlbums() : LiveData<List<Album>>

    @Query("SELECT *, count(collectionId) as number_of_views FROM album_table GROUP BY collectionId ORDER BY number_of_views DESC")
    fun takeMostViewedAlbums() : LiveData<List<Album>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJustViewed(album: Album)
}