package com.example.iplayer.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ITunesApi {

    companion object {
        private const val SEARCH = "search"
        private const val LOOKUP = "lookup"
    }

    @GET(value = SEARCH)
    fun ping() : Deferred<Response<ITunesResponse>>


    @GET(value = SEARCH)
    fun searchSongByTerm(
        @Query("term") term: String,
        @QueryMap attribute: Map<String, String>? = emptyMap(),
        @Query("entity") entity: String = Entity.SONG.eName,
        @Query("media") media: String = "music"
    ) : Deferred<Response<ITunesResponse>>

    @GET(value = SEARCH)
    fun searchAlbumByTerm(
        @Query("term") term: String,
        @QueryMap attribute: Map<String, String>? = null,
        @Query("entity") entity: String = Entity.ALBUM.eName,
        @Query("media") media: String = "music"
    ) : Deferred<Response<ITunesResponse>>

    @GET(value = LOOKUP)
    fun searchAllTracksFromAlbum(
        @Query("id") collectionId: Int,
        @Query("entity") entity: String = Entity.SONG.eName,
        @Query("media") media: String = "music"
    ) : Deferred<Response<ITunesResponse>>

    @GET(value = SEARCH)
    fun searchArtistmByTerm(
        @Query("term") term: String,
        @QueryMap attribute: Map<String, String>? = emptyMap(),
        @Query("entity") entity: String = Entity.ARTIST.eName,
        @Query("media") media: String = "music"
    ) : Deferred<Response<ITunesResponse>>

    enum class Entity(val eName: String) {
        SONG("song"),
        ALBUM("album"),
        ARTIST("arstist");
    }

    sealed class Result<out T: Any> {
        data class Success<out T: Any>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }

    /*
    song
    {"wrapperType":"track", "kind":"song", "artistId":5040714, "collectionId":574049507, "trackId":574051407, "artistName":"AC/DC", "collectionName":"The Razors Edge", "trackName":"Thunderstruck", "collectionCensoredName":"The Razors Edge", "trackCensoredName":"Thunderstruck", "artistViewUrl":"https://music.apple.com/us/artist/ac-dc/5040714?uo=4", "collectionViewUrl":"https://music.apple.com/us/album/thunderstruck/574049507?i=574051407&uo=4", "trackViewUrl":"https://music.apple.com/us/album/thunderstruck/574049507?i=574051407&uo=4", "previewUrl":"https://audio-ssl.itunes.apple.com/itunes-assets/Music/v4/28/20/d8/2820d8ee-5c1c-a043-4e0c-a6599992b77c/mzaf_5702610131159527568.plus.aac.p.m4a", "artworkUrl30":"https://is4-ssl.mzstatic.com/image/thumb/Music/v4/81/17/f9/8117f9c5-ba41-97e8-1de5-cf75b0d4cc5f/source/30x30bb.jpg", "artworkUrl60":"https://is4-ssl.mzstatic.com/image/thumb/Music/v4/81/17/f9/8117f9c5-ba41-97e8-1de5-cf75b0d4cc5f/source/60x60bb.jpg", "artworkUrl100":"https://is4-ssl.mzstatic.com/image/thumb/Music/v4/81/17/f9/8117f9c5-ba41-97e8-1de5-cf75b0d4cc5f/source/100x100bb.jpg", "collectionPrice":9.99, "trackPrice":1.29, "”":"1990-09-10T07:00:00Z", "collectionExplicitness":"notExplicit", "trackExplicitness":"notExplicit", "discCount":1, "discNumber":1, "trackCount":12, "trackNumber":1, "trackTimeMillis":292334, "country":"USA", "currency":"USD", "primaryGenreName":"Hard Rock", "isStreamable":true},
    album
    "wrapperType":"collection", "collectionType":"Album", "artistId":1218980547, "collectionId":1223088234, "artistName":"Aussie Rock All Stars", "collectionName":"Thunderstruck: A Tribute to AC/DC Greatest Hits", "collectionCensoredName":"Thunderstruck: A Tribute to AC/DC Greatest Hits", "artistViewUrl":"https://music.apple.com/us/artist/aussie-rock-all-stars/1218980547?uo=4", "collectionViewUrl":"https://music.apple.com/us/album/thunderstruck-a-tribute-to-ac-dc-greatest-hits/1223088234?uo=4", "artworkUrl60":"https://is3-ssl.mzstatic.com/image/thumb/Music122/v4/6c/66/e6/6c66e6d9-40d5-e992-cb47-e95ca492f41d/source/60x60bb.jpg", "artworkUrl100":"https://is3-ssl.mzstatic.com/image/thumb/Music122/v4/6c/66/e6/6c66e6d9-40d5-e992-cb47-e95ca492f41d/source/100x100bb.jpg", "collectionPrice":9.99, "collectionExplicitness":"notExplicit", "trackCount":11, "copyright":"℗ 2017 Aussie Rock All Stars", "country":"USA", "currency":"USD", "releaseDate":"2017-03-25T07:00:00Z", "primaryGenreName":"Rock"}
    musicArtist
    {"wrapperType":"artist", "artistType":"Artist", "artistName":"AC/DC", "artistLinkUrl":"https://music.apple.com/us/artist/ac-dc/5040714?uo=4", "artistId":5040714, "amgArtistId":3496, "primaryGenreName":"Hard Rock", "primaryGenreId":1152}
     */

}