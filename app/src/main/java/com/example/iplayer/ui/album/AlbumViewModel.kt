package com.example.iplayer.ui.album

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iplayer.data.Song
import com.example.iplayer.repositories.ITunesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumViewModel(
    private val iTunesRepository: ITunesRepository
) : ViewModel() {

    val allAlbumTracks = MutableLiveData<List<Song>>()

    fun searchAllTracksById(id: Int) = viewModelScope.launch(context = Dispatchers.IO) {
        val listOfTracks = iTunesRepository.searchITunesAllTracksFromAlbum(id)
        if (listOfTracks != null && listOfTracks.isNotEmpty())
            viewModelScope.launch {
                allAlbumTracks.value = listOfTracks
            }
    }
}
