package com.example.iplayer.repositories

import com.example.iplayer.data.Album
import com.example.iplayer.data.Artist
import com.example.iplayer.data.Song
import com.example.iplayer.network.ITunesApi
import com.example.iplayer.network.ITunesApi.Entity
import com.example.iplayer.network.ITunesBaseRepository
import com.example.iplayer.network.ITunesEntity
import javax.inject.Inject

class ITunesRepository @Inject constructor (
    private val iTunesApi: ITunesApi
) : ITunesBaseRepository() {

    suspend fun searchITunesSong(term: String) : List<Song>? {
        val iResponse = safeApiCall(
            call = { iTunesApi.searchSongByTerm(term).await() },
            errorMessage = "Error searching for SONG by term: \"$term\""
        )

        return iResponse?.results?.map { Song(it) }
    }

    suspend fun searchITunesAlbum(term: String) : List<Album>? {
        val iResponse = safeApiCall(
            call = { iTunesApi.searchSongByTerm(term).await() },
            errorMessage = "Error searching for SONG by term: \"$term\""
        )

        return iResponse?.results?.map { Album(it) }
    }

    suspend fun searchITunesArtist(term: String) : List<Artist>? {
        val iResponse = safeApiCall(
            call = { iTunesApi.searchSongByTerm(term).await() },
            errorMessage = "Error searching for SONG by term: \"$term\""
        )

        return iResponse?.results?.map { Artist(it) }
    }

    suspend fun searchITunes(term: String, entity: Entity) : List<ITunesEntity>? {

        val iResponse = when (entity) {
            Entity.SONG -> safeApiCall(
                call = { iTunesApi.searchSongByTerm(term).await() },
                errorMessage = "Error searching for ${entity.eName} by term: \"$term\""
            )
            Entity.ALBUM -> safeApiCall(
                call = { iTunesApi.searchAlbumByTerm(term).await() },
                errorMessage = "Error searching for ${entity.eName} by term: \"$term\""
            )
            Entity.ARTIST -> safeApiCall(
                call = { iTunesApi.searchArtistmByTerm(term).await() },
                errorMessage = "Error searching for ${entity.eName} by term: \"$term\""
            )
        }

        return iResponse?.results
    }

}