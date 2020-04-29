package com.example.iplayer.database

import androidx.room.Entity
import java.util.*
import com.example.iplayer.data.Album as DomainAlbum

@Entity(
    tableName = "album_table",
    primaryKeys = [
        "collectionId",
        "dateViewed"
    ]
)
data class Album (
    val dateViewed: Date,

    val artistName : String,
    val collectionName : String,
    val collectionId :  Int,
    val artworkUrl100 : String,
    val trackCount : Int,
    val country : String,
    val releaseDate : Date,
    val primaryGenreName : String
) {
    constructor(album: DomainAlbum, dateViewed: Date) : this (
                dateViewed, album.artistName, album.collectionName, album.collectionId,
                album.artworkUrl100, album.trackCount, album.country, album.releaseDate,
                album.primaryGenreName
            )
}

//data class AlbumMinimal