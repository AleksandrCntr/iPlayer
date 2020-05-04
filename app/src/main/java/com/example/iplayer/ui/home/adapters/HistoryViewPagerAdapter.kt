package com.example.iplayer.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.iplayer.R
import com.example.iplayer.data.Album
import com.example.iplayer.getReleaseDateYear
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_viewpager_album.view.*

class HistoryViewPagerAdapter(
    val musicList: List<Album>,
    val pageWidth: Float
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_viewpager_album, container, false)
        val item = musicList[position]
        with(view) {
            Picasso.get().load(item.artworkUrl100).into(artwork)
            albumName.text = item.collectionName
            bandName.text = "$item.artistName ${item.getReleaseDateYear()}"
        }
        container.addView(view)
        return view
    }

    override fun getCount() = musicList.size

    override fun getPageWidth(position: Int) = pageWidth

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}