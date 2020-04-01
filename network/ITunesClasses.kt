package com.example.iplayer.network

data class ITunesResponse (
    val resultCount : Long,
    val results : List<ITunesEntity>
)

data class ITunesEntity (
    val wrapperType : String,
    val kind : String,
    val artistId : Int,
    val collectionId : Int,
    val trackId : Int,
    val artistName : String,
    val collectionName : String,
    val trackName : String,
    val collectionCensoredName : String,
    val trackCensoredName : String,
    val artistViewUrl : String,
    val collectionViewUrl : String,
    val trackViewUrl : String,
    val previewUrl : String,
    val artworkUrl60 : String,
    val artworkUrl100 : String,
    val collectionPrice : Float,
    val trackPrice : Float,
    val collectionExplicitness : String,
    val trackExplicitness : String,
    val discCount : Int,
    val discNumber : Int,
    val trackCount : Int,
    val trackNumber : Int,
    val trackTimeMillis : Int,
    val country : String,
    val currency : String,
    val primaryGenreName : String
)