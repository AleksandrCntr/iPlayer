package com.example.iplayer.data

data class GenreSuggestion(
    val genre : String,
    val albums : List<Album>
) : MusicSuggestion(MusicSuggestionType.GENRE_SUGGESTION)

data class SingleAlbumSuggestion(
    val similarBandName : String,
    val album: Album
) : MusicSuggestion(MusicSuggestionType.SINGLE_ALBUM_SUGGESTION)


sealed class MusicSuggestion (
    val viewType: MusicSuggestionType
)

//  Is there a simpler way? Recycler view requires some Int to be passed to onCreateViewHolder
//representing the view type
enum class MusicSuggestionType(val viewTypeNumber: Int) {
    GENRE_SUGGESTION(0),
    SINGLE_ALBUM_SUGGESTION(1)
}