package com.example.iplayer.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iplayer.R
import com.example.iplayer.data.Album
import com.example.iplayer.getReleaseDateYear
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumListAdapter(
//    private val context: Context,
    private val albumList: List<Album>,
    private val itemClick: (album: Album) -> Unit
) : RecyclerView.Adapter<AlbumViewHolder>() {

//    private val numberOfTracks by bindString(context, R.string.numberOfTracks)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false),
            itemClick
        )
    }

    override fun getItemCount() = albumList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bindAlbum(albumList[position])
    }

    override fun onViewRecycled(holder: AlbumViewHolder) {
        holder.cleanUp()
        super.onViewRecycled(holder)
    }
}

class AlbumViewHolder(
    override val containerView : View,
    private val itemClick: (Album: Album) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindAlbum(album: Album) {
        with(album) {
            if (artworkUrl100.isNotEmpty())
                Picasso.get().load(artworkUrl100).into(itemView.artwork)

            itemView.albumName.text = collectionName

            //  todo bindString from resources
            itemView.numberOfTracks.text = "$trackCount tracks"

            itemView.bandName.text = artistName

            itemView.releaseYear.text = album.getReleaseDateYear()

            itemView.setOnClickListener { itemClick(album) }
        }
    }

    fun cleanUp() {
        // todo Do i have to check if it is in progress
        Picasso.get().cancelRequest(itemView.artwork)
    }

}