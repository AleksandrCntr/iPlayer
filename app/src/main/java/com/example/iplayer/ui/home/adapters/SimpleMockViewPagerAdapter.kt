package com.example.iplayer.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter

class SimpleMockViewPagerAdapter(
    @LayoutRes val layoutId: Int,
    val pageWidth: Float
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(layoutId, container, false)
        container.addView(view)
        return view
    }

    override fun getCount() = 20

    override fun getPageWidth(position: Int): Float {
        return pageWidth
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}