package com.example.iplayer.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.iplayer.R
import com.example.iplayer.data.Album
import com.example.iplayer.getReleaseDateYear
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_viewpager_album.view.*

class AlbumViewPagerAdapter(
    private var albumList: List<Album>,
    val pageWidth: Float,
    private val itemClick: (album: Album) -> Unit
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_viewpager_album, container, false)
        val item = albumList[position]
        view.apply {
            Picasso.get().load(item.artworkUrl100).into(artwork)
            albumName.text = item.collectionName
            bandNameAndYear.text = "${item.artistName}, ${item.getReleaseDateYear()}"
            setOnClickListener { itemClick(item) }
        }
        container.addView(view)
        return view
    }

    override fun getCount() = albumList.size

    override fun getPageWidth(position: Int) = pageWidth

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}