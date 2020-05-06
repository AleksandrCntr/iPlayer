package com.example.iplayer.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iplayer.R
import com.example.iplayer.data.Album
import com.squareup.picasso.Picasso

//  I consider it a better solution that putting small recycler view into it
//todo think on scalability

class StackAlbumAdapter(
    private val listOfThreeStackedAlbums : List<List<Album>>
) : RecyclerView.Adapter<StackedAlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackedAlbumViewHolder {
        return StackedAlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_three_albums, parent, false)
        )
    }

    override fun getItemCount() = listOfThreeStackedAlbums.size

    override fun onBindViewHolder(holder: StackedAlbumViewHolder, position: Int) {
        holder.bindStackAlbums(listOfThreeStackedAlbums[position])
    }

}

class StackedAlbumViewHolder(
    val containerView: View
) : RecyclerView.ViewHolder(containerView) {

    fun bindStackAlbums(stackedAlbums : List<Album>) {
        val childViews = with(containerView as ViewGroup) { (0 until childCount).map { getChildAt(it) } }

        //  every child view is a item_single_album
        childViews.forEachIndexed {
                i, it ->
            stackedAlbums.getOrNull(i)?.let {
                    album ->
                Picasso.get().load(album.artworkUrl100).into(
                    it.findViewById<ImageView>(R.id.artwork)
                )
                it.findViewById<TextView>(R.id.albumName).text = album.collectionName
                it.findViewById<TextView>(R.id.bandName).text = album.artistName
            }
        }

    }

}