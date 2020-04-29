package com.example.iplayer.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iplayer.data.Album
import com.example.iplayer.data.DomainITunesEntity
import com.example.iplayer.network.ITunesApi
import com.example.iplayer.repositories.ITunesRepository
import com.example.iplayer.repositories.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel (
    private val iTuneRepo: ITunesRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    val musicSearchResult = MutableLiveData<List<DomainITunesEntity>>()
    private val musicSearchType = MutableLiveData<ITunesApi.Entity>()

    private var previousITunesSearchJob : Job? = null

    val recentlyViewedAlbums = roomRepository.takeLatestThirtyDistinctAlbums()
    val mostViewedAlbums = roomRepository.takeMostViewedAlbums()

    init {
        musicSearchType.value = ITunesApi.Entity.SONG
    }

    fun getCurrentSearchTypeValue() = musicSearchType.value

    fun setMusicSearchType(entity: ITunesApi.Entity) {
        musicSearchType.value = entity
    }

    fun searchITunes(term: String) {
        previousITunesSearchJob?.let { if (it.isActive) it.cancel() }
        previousITunesSearchJob = viewModelScope.launch(context = Dispatchers.IO) {
            //  todo can mutable livedata, preloaded, be null
            val searchResult = when (getCurrentSearchTypeValue()!!) {
                ITunesApi.Entity.SONG -> iTuneRepo.searchITunesSong(term)
                ITunesApi.Entity.ALBUM -> iTuneRepo.searchITunesAlbum(term)
                ITunesApi.Entity.ARTIST -> iTuneRepo.searchITunesArtist(term)
            }

            viewModelScope.launch {
                searchResult?.let { musicSearchResult.value = it }
            }
        }
    }

    fun insertJustViewedAlbum(album: Album) = viewModelScope.launch(context = Dispatchers.IO) {
        roomRepository.insertJustViewedAlbum(album)
    }

    fun getRecentlyViewedAlbumsValue() = recentlyViewedAlbums.value
    fun getMostViewedAlbumsValue() = mostViewedAlbums.value
}



//    internal val searchQueryChannel = BroadcastChannel<String>(Channel.CONFLATED)
//    @ExperimentalCoroutinesApi
//    @FlowPreview
//    internal val searchResultFlow : Flow<Unit> = searchQueryChannel.asFlow()
//            .debounce(300)
//            .mapLatest {
//                try {
//                    if (it.length > 4) {
//                        searchITunes(it)
//                    }
//                } catch (e: CancellationException) {
//                    Log.d("Search Result", "More text received - search operation cancelled!")
//                    throw e
//                }
//            }
//            .catch {
//                    it: Throwable ->
//                Log.d("Search Result", it.toString())
//            }