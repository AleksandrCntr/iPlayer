package com.example.iplayer.network

import com.squareup.moshi.Json

data class ITunesResponse (
    @field:Json(name = "resultCount") val resultCount : Long,
    @field:Json(name = "results") val results : List<ITunesEntity>
)

data class ITunesEntity (
    @field:Json(name = "wrapperType") val wrapperType : String,
    @field:Json(name = "kind") val kind : String,
    @field:Json(name = "artistId") val artistId : Int,
    @field:Json(name = "collectionId") val collectionId : Int,
    @field:Json(name = "trackId") val trackId : Int,
    @field:Json(name = "artistName") val artistName : String,
    @field:Json(name = "collectionName") val collectionName : String,
    @field:Json(name = "trackName") val trackName : String,
    @field:Json(name = "collectionCensoredName") val collectionCensoredName : String,
    @field:Json(name = "trackCensoredName") val trackCensoredName : String,
    @field:Json(name = "artistViewUrl") val artistViewUrl : String,
    @field:Json(name = "collectionViewUrl") val collectionViewUrl : String,
    @field:Json(name = "trackViewUrl") val trackViewUrl : String,
    @field:Json(name = "previewUrl") val previewUrl : String,
    @field:Json(name = "artworkUrl60") val artworkUrl60 : String,
    @field:Json(name = "artworkUrl100") val artworkUrl100 : String,
    @field:Json(name = "collectionPrice") val collectionPrice : Float,
    @field:Json(name = "trackPrice") val trackPrice : Float,
    @field:Json(name = "collectionExplicitness") val collectionExplicitness : String,
    @field:Json(name = "trackExplicitness") val trackExplicitness : String,
    @field:Json(name = "discCount") val discCount : Int,
    @field:Json(name = "discNumber") val discNumber : Int,
    @field:Json(name = "trackCount") val trackCount : Int,
    @field:Json(name = "trackNumber") val trackNumber : Int,
    @field:Json(name = "trackTimeMillis") val trackTimeMillis : Int,
    @field:Json(name = "country") val country : String,
    @field:Json(name = "currency") val currency : String,
    @field:Json(name = "primaryGenreName") val primaryGenreName : String
)