package com.example.iplayer.ui.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iplayer.R
import com.example.iplayer.data.Song
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_album_track.view.*

class SongListAdapter(
    private val songList: List<Song>,
    private val itemClick: (song: Song) -> Unit
) : RecyclerView.Adapter<SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album_track, parent, false)
        return SongViewHolder(view, itemClick)
    }

    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songList[position])
    }
}

class SongViewHolder(
    override val containerView : View,
    private val itemClick : (song: Song) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(song: Song) {
        with(song) {
            itemView.trackNumber.text = trackNumber.toString()
            itemView.trackName.text = trackName
            itemView.trackLength.text = trackTimeMillis.toString()

            itemView.setOnClickListener { itemClick (song) }
        }
    }

    companion object {
        private fun millisToMinutesFormat(millis : Int) {
            val formatedTime = millis / 60
        }
    }
}