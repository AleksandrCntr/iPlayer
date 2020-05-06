package com.example.iplayer.ui.home.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iplayer.R
import com.example.iplayer.data.GenreSuggestion
import com.example.iplayer.data.MusicSuggestion
import com.example.iplayer.data.MusicSuggestionType
import com.example.iplayer.data.SingleAlbumSuggestion
import com.example.iplayer.getReleaseDateYear
import com.squareup.picasso.Picasso

class SuggestionListAdapter(
    private val suggestionList : List<MusicSuggestion>
//    private val itemClick: (album : Album) -> Unit
) : RecyclerView.Adapter<SuggestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {

        val viewId = when (viewType) {

                MusicSuggestionType.SINGLE_ALBUM_SUGGESTION.viewTypeNumber ->
                    R.layout.item_suggestion_signle_album

                MusicSuggestionType.GENRE_SUGGESTION.viewTypeNumber ->
                    R.layout.item_suggestion_genre

                else -> R.layout.item_suggestion_signle_album
            }

        return SuggestionViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(viewId, parent, false)
        )
    }

    override fun getItemViewType(position: Int) =
        suggestionList[position].viewType.viewTypeNumber


    override fun getItemCount() = suggestionList.size

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bindSuggestion(suggestionList[position])
    }

}

class SuggestionViewHolder(
    val context: Context,
    val containerView: View
//    private val itemClick: (album: Album) -> Unit
) : RecyclerView.ViewHolder(containerView) {

    fun bindSuggestion(ms : MusicSuggestion) {
        when (ms) {
            is SingleAlbumSuggestion -> bindSingleAlbumSuggestion(ms)
            is GenreSuggestion -> bindGenreSuggestion(ms)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindSingleAlbumSuggestion(ms : SingleAlbumSuggestion) {
        val similarBandName = containerView.findViewById<TextView>(R.id.similarBandName)
        val artwork = containerView.findViewById<ImageView>(R.id.artwork)
        val albumName = containerView.findViewById<TextView>(R.id.albumName)
        val bandNameAndYear = containerView.findViewById<TextView>(R.id.bandAndYear)
        val numberOfTracks = containerView.findViewById<TextView>(R.id.numberOfTracks)
//        val btnPlayAll = containerView.findViewById<Button>(R.id.btnPlayAll)

        similarBandName.text = ms.similarBandName

        ms.album.artworkUrl100.let {
            if (it.isNotEmpty())
                Picasso.get().load(it).into(artwork)
        }
        albumName.text = ms.album.collectionName
        bandNameAndYear.text = "${ms.album.artistName}, ${ms.album.getReleaseDateYear()}"
        numberOfTracks.text = "${ms.album.trackCount} tracks"

        // todo configure button play all
//        btnPlayAll.setOnClickListener {  }
    }

    private fun bindGenreSuggestion(ms : GenreSuggestion) {
        val similarGenreName = containerView.findViewById<TextView>(R.id.similarGenreName)
        val stackedAlbumsRecyclerView = containerView.findViewById<RecyclerView>(R.id.stackedSuggestionAlbums)

        similarGenreName.text = ms.genre

        //todo adapter is accepting only 3 albums rn
        stackedAlbumsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        stackedAlbumsRecyclerView.adapter = StackAlbumAdapter(ms.albums.chunked(3))
    }

}