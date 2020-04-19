package com.example.iplayer.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iplayer.network.ITunesApi
import com.example.iplayer.network.ITunesEntity
import com.example.iplayer.repositories.ITunesRepository
import kotlinx.coroutines.launch

class HomeViewModel (
    private val iTuneRepo: ITunesRepository
) : ViewModel() {

    val musicSearchResult = MutableLiveData<List<ITunesEntity>>()

    fun searchITunes(term: String, entity: ITunesApi.Entity) = viewModelScope.launch {
        iTuneRepo.searchITunes(term, entity)?.also {
            musicSearchResult.value = it
        }
    }

}
