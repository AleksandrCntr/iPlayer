package com.example.iplayer.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iplayer.R
import com.example.iplayer.data.Song
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_single_album_somg_two.view.*

class SongListAdapter(
    private val songList: List<Song>,
    private val itemClick: (song: Song) -> Unit
) : RecyclerView.Adapter<SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_single_album_somg_two, parent, false),
            itemClick
        )
    }

    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bindSong(songList[position])
    }

    override fun onViewRecycled(holder: SongViewHolder) {
        holder.cleanUp()
        super.onViewRecycled(holder)
    }
}

class SongViewHolder(
    override val containerView : View,
    private val itemClick: (song: Song) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindSong(song: Song) {
        with(song) {
            if (artworkUrl100.isNotEmpty())
                Picasso.get().load(artworkUrl100).into(itemView.artwork)
            itemView.bandName.text = artistName
            itemView.songName.text = trackName

            itemView.setOnClickListener { itemClick(song) }
        }
    }

    fun cleanUp() {
        // todo Do i have to check if it is in progress
        Picasso.get().cancelRequest(itemView.artwork)
    }

}