package com.example.iplayer.data

import com.example.iplayer.network.ITunesEntity
import java.util.*
import com.example.iplayer.database.Album as RoomAlbum

data class Song(
    val trackName: String,
    val trackNumber: Int,
    val primaryGenreName: String,
    val trackTimeMillis: Int,
    val artistName: String,
    val country: String,
    val collectionName : String,
    val previewUrl: String,
    val artworkUrl100 : String
) : DomainITunesEntity {
    constructor(iTunesEntity: ITunesEntity) : this(
            iTunesEntity.trackName, iTunesEntity.trackNumber, iTunesEntity.primaryGenreName,
            iTunesEntity.trackTimeMillis, iTunesEntity.artistName, iTunesEntity.country, iTunesEntity.collectionName,
            iTunesEntity.previewUrl, iTunesEntity.artworkUrl100)
}

data class Album(
    val artistName : String,
    val collectionName : String,
    val collectionId :  Int,
    val artworkUrl100 : String,
    val trackCount : Int,
    val country : String,
    val releaseDate : Date,
    val primaryGenreName : String
) : DomainITunesEntity {
    constructor(iTunesEntity: ITunesEntity) : this(
            iTunesEntity.artistName, iTunesEntity.collectionName, iTunesEntity.collectionId,
            iTunesEntity.artworkUrl100, iTunesEntity.trackCount, iTunesEntity.country,
            iTunesEntity.releaseDate, iTunesEntity.primaryGenreName)
    constructor(roomAlbum: RoomAlbum) : this(
            roomAlbum.artistName, roomAlbum.collectionName, roomAlbum.collectionId,
            roomAlbum.artworkUrl100, roomAlbum.trackCount, roomAlbum.country,
            roomAlbum.releaseDate, roomAlbum.primaryGenreName
    )
}

data class Artist(
    val artistName: String
) : DomainITunesEntity {
    constructor(iTunesEntity: ITunesEntity) : this(
        iTunesEntity.artistName
    )
}

interface DomainITunesEntity