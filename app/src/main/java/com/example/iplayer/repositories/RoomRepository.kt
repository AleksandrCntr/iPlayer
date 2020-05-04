package com.example.iplayer.repositories

import com.example.iplayer.data.Album
import com.example.iplayer.database.dao.AlbumDao
import java.util.*
import com.example.iplayer.database.Album as RoomAlbum

class RoomRepository(
    private val albumDao: AlbumDao
) {
    fun takeLatestThirtyDistinctAlbums() = albumDao.takeLatestThirtyDistinctAlbums()

    fun takeMostViewedAlbums() = albumDao.takeMostViewedAlbums()

    suspend fun insertJustViewedAlbum(album: Album) =
        albumDao.insertJustViewed(
            RoomAlbum(album, Calendar.getInstance().time)
        )
}
