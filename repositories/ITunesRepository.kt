package com.example.iplayer.repositories

import com.example.iplayer.network.ITunesApi
import com.example.iplayer.network.ITunesBaseRepository
import com.example.iplayer.network.ITunesEntity

class ITunesRepository(
    private val iTunesApi: ITunesApi
) : ITunesBaseRepository() {

    suspend fun simpleSearchSongByTerm(term : String): List<ITunesEntity>? {
        val iResponse = safeApiCall(
            call = { iTunesApi.searchSongByTerm(term).await() },
            errorMessage = "Error searching for song by term: \"$term\""
        )
        return iResponse?.results
    }
//
//    suspend fun simpleSearchAlbumByTerm(term : String): List<ITunesEntity>? {
//        val iResponse = safeApiCall(
//            call = { iTunesApi.searchAlbumByTerm(term).await() },
//            errorMessage = "Error searching for album by term: \"$term\""
//        )
//        return iResponse?.results
//    }

}