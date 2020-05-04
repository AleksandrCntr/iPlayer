package com.example.iplayer.ui.album

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.marginTop
import com.example.iplayer.R
import com.google.android.material.appbar.AppBarLayout

class AlbumAppBarCoordinatorBehaviour : CoordinatorLayout.Behavior<AppBarLayout>() {

    private lateinit var appbarInnerContainer : View
    private lateinit var albumInfoContainer : View
    private lateinit var bandNameTop : View
    private lateinit var appbarBg : View
    private lateinit var artworkContainer : View
    private lateinit var albumName : View
    private lateinit var bandName : View
    private lateinit var genreAndYear : View

    private var appbarCollapsedHeight : Int = -1
    private var appbarOriginalHeight : Int = -1
    private var appbarOriginalHeightOneThird : Int = -1

    //Snap behaviour
    private var snapAnimator : ValueAnimator? = null
    private var nestedScrollStarted : Boolean = false


    private var defineViewsFlag = false

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        nestedScrollStarted = axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)

        snapAnimator?.let {
            if (nestedScrollStarted) it.cancel()
        }

        return nestedScrollStarted
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
        if (dyConsumed > 0) {
            //scrolling up, perform hiding, shrinking
            if (appbarInnerContainer.height > appbarCollapsedHeight) {

                //  shrinking appbar container
                var appBarHeight = appbarInnerContainer.height - dyConsumed
                if (appBarHeight < appbarCollapsedHeight) appBarHeight = appbarCollapsedHeight
                appbarInnerContainer.layoutParams.height = appBarHeight
                appbarInnerContainer.requestLayout()

                //  fade alpha
                var ratio = appbarCollapsedHeight / appBarHeight.toFloat()
                val invertedRatio = 1f - ratio

                albumInfoContainer.alpha = invertedRatio
                artworkContainer.alpha = invertedRatio

                //  show band name at top
                if (appBarHeight < appbarOriginalHeightOneThird) {
                    bandNameTop.alpha = ratio
                }
            }

        } else if (dyUnconsumed < 0) {
            //scrolling down, perform revealing, expanding
            if (appbarInnerContainer.height < appbarOriginalHeight) {

                //  expanding appbar container
                var appBarHeight = appbarInnerContainer.height - dyUnconsumed
                if (appBarHeight > appbarOriginalHeight) appBarHeight = appbarOriginalHeight
                appbarInnerContainer.layoutParams.height = appBarHeight
                appbarInnerContainer.requestLayout()

                //  reveal alpha
                var ratio = appBarHeight.toFloat() / appbarOriginalHeight
                albumInfoContainer.alpha = ratio
                artworkContainer.alpha = ratio

                if (appBarHeight <= appbarOriginalHeightOneThird) {
                    bandNameTop.alpha = 1 - ( appBarHeight / appbarOriginalHeightOneThird.toFloat() )
                } else { if (bandNameTop.alpha > 0f) bandNameTop.alpha = 0f }
            }

        }
    }



    //**********************
    //* Snapping behaviour *
    //**********************
    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, type: Int) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)

        if (!nestedScrollStarted) return

        nestedScrollStarted = false

        //  if user scrolled a little bit up reveal everything
        val appbarHeight = appbarInnerContainer.height
        if (appbarHeight < appbarOriginalHeight || appbarHeight > appbarCollapsedHeight) {
            val snapToTop = appbarInnerContainer.height < (appbarOriginalHeight - 250)
            animateSizeAndAlpha(snapToTop)
        }
    }
    private fun animateSizeAndAlpha(snapToTop: Boolean) {
        snapAnimator = ValueAnimator()

        snapAnimator?.let {
            if (snapToTop) {
                it.apply {
                    interpolator = AccelerateInterpolator()
                    addUpdateListener {
                        val value = it.animatedValue as Int
                        appbarInnerContainer.layoutParams.height = value
                        appbarInnerContainer.requestLayout()

                        //  fade alpha
                        val ratio = appbarCollapsedHeight / value.toFloat()
                        val invertedRatio = 1f - ratio

                        albumInfoContainer.alpha = invertedRatio
                        artworkContainer.alpha = invertedRatio

                        //  show band name at top
                        if (value < appbarOriginalHeightOneThird) {
                            bandNameTop.alpha = ratio
                        }
                    }
                    setIntValues(appbarInnerContainer.height, appbarCollapsedHeight)
                }
            } else {
                it.apply {
                    interpolator = AccelerateInterpolator()
                    addUpdateListener {
                        val value = it.animatedValue as Int
                        appbarInnerContainer.layoutParams.height = value
                        appbarInnerContainer.requestLayout()

                        //  fade alpha
                        val ratio = value.toFloat() / appbarOriginalHeight
                        albumInfoContainer.alpha = ratio
                        artworkContainer.alpha = ratio

                        //  show band name at top
                        if (value <= appbarOriginalHeightOneThird) {
                            bandNameTop.alpha = 1 - ( value / appbarOriginalHeightOneThird.toFloat() )
                        }
                    }
                    setIntValues(appbarInnerContainer.height, appbarOriginalHeight)
                }
            }
            it.start()
        }
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: AppBarLayout, layoutDirection: Int): Boolean {
        getViews(child)
//        (parent.findViewById(R.id.albumNestedScrollView) as NestedScrollView).isNestedScrollingEnabled = false
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    private fun getViews(child: AppBarLayout) {
        if (defineViewsFlag) return
        defineViewsFlag = true

        appbarInnerContainer = child.findViewById(R.id.appbarInnerContainer)
        albumInfoContainer = child.findViewById(R.id.albumInfoContainer)
        bandNameTop = child.findViewById(R.id.bandNameTop)
        appbarBg = child.findViewById(R.id.appbarBg)
        artworkContainer = child.findViewById(R.id.artworkContainer)
        albumName = child.findViewById(R.id.albumName)
        bandName = child.findViewById(R.id.bandName)
        genreAndYear = child.findViewById(R.id.genreAndYear)

        appbarOriginalHeight = child.measuredHeight
        appbarOriginalHeightOneThird = appbarOriginalHeight / 3

        val btnBack = child.findViewById(R.id.btnBack) as View
        appbarCollapsedHeight = btnBack.measuredHeight + ( 2 * btnBack.marginTop )
    }

}