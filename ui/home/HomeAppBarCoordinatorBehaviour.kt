package com.example.iplayer.ui.home

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.marginTop
import com.example.iplayer.R
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class HomeAppBarCoordinatorBehaviour : CoordinatorLayout.Behavior<AppBarLayout>() {

    /*
    todo 1. when exectly can i call getViews Once
     */

    /*
    ToolbarSearchPart is bigger so we need to find its height and layer it over Title part
     */

    private lateinit var toolbarPartsHolder : View

    private lateinit var toolbarTopPart : View
    private lateinit var toolbarTitle : View
    private lateinit var toolbarBtn : View

    private lateinit var toolbarSearchPart : View

    private var toolbarTopPartHeight = -1
    private var toolbarSearchPartHeight = -1


    //Snap behaviour
    private var snapAnimator : ValueAnimator? = null
    private var nestedScrollStarted : Boolean = false


    private var defineViewsFlag = false


    private var searchingMode = false

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        getViews(child)

        nestedScrollStarted = axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)

        snapAnimator?.let {
            if (nestedScrollStarted) it.cancel()
        }

        return nestedScrollStarted
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
        getViews(child)

        if (dyConsumed > 0) {
            //scrolling up, perform hiding, shrinking
            //  set a negative toolbarSearchPart margin to layer over top part

            if (abs(toolbarSearchPart.marginTop) < toolbarTopPartHeight) {
                var toolbarSearchPartMarginTop = toolbarSearchPart.marginTop - dyConsumed
                if (abs(toolbarSearchPartMarginTop) > toolbarTopPartHeight) toolbarSearchPartMarginTop = -toolbarTopPartHeight

                toolbarSearchPart.changeTopMarginAndApply(toolbarSearchPartMarginTop)

                val topPartAlpha = abs(toolbarSearchPartMarginTop) / toolbarTopPartHeight.toFloat() * 1.3f
                fadeAlpha(topPartAlpha)
            }

        } else if (dyUnconsumed < 0) {
            //scrolling down, perform revealing, expanding
            //  increase a negative toolbarSearchPart margin to reveal top part
            //  if searchingMode is on, we dont want to reveal top part
            if (!searchingMode && toolbarSearchPart.marginTop < 0) {
                var toolbarSearchPartMarginTop = toolbarSearchPart.marginTop - dyUnconsumed
                if (toolbarSearchPartMarginTop > 0) toolbarSearchPartMarginTop = 0

                toolbarSearchPart.changeTopMarginAndApply(toolbarSearchPartMarginTop)

                val topPartAlpha = (toolbarTopPartHeight - abs(toolbarSearchPartMarginTop)) / toolbarTopPartHeight.toFloat() * 1.3f
                revealAlpha(topPartAlpha)
            }

        }
    }

    private fun View.changeTopMarginAndApply(topMargin : Int) {
        (this.layoutParams as ViewGroup.MarginLayoutParams).topMargin = topMargin
        this.requestLayout()
    }

    private fun fadeAlpha(alpha: Float) {
        var topPartAlpha = alpha
        if (topPartAlpha > 1f) topPartAlpha = 1f

        toolbarTitle.alpha = 1f - topPartAlpha
        toolbarBtn.alpha = 1f - topPartAlpha
    }

    private fun revealAlpha(alpha: Float) {
        var topPartAlpha = alpha
        if (topPartAlpha > 1f) topPartAlpha = 1f

        toolbarTitle.alpha = topPartAlpha
        toolbarBtn.alpha = topPartAlpha
    }

    //**********************
    //* Snapping behaviour *
    //**********************
    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, type: Int) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)

        if (!nestedScrollStarted) return

        nestedScrollStarted = false

        if (toolbarSearchPart.marginTop < 0) {
            //then it is moving
            val snapToZero = abs(toolbarSearchPart.marginTop) < toolbarTopPartHeight / 2
            animateMarginAndAlpha(toolbarSearchPart, snapToZero, toolbarTopPartHeight).start()
        }
    }

    //  Non user interaction method, hiding title toolbar part when entering search
    fun animateHideTitleToolbar(): ValueAnimator? {
        if (abs(toolbarSearchPart.marginTop) < toolbarTopPartHeight) {
            return animateMarginAndAlpha(toolbarSearchPart, false, toolbarTopPartHeight)
        }
        return null
    }

    fun animateMarginAndAlpha(view: View, snapToZero: Boolean, fullHeight: Int): ValueAnimator {
        snapAnimator = ValueAnimator().apply {
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                view.changeTopMarginAndApply(it.animatedValue as Int)
                if (snapToZero) {
                    revealAlpha((toolbarTopPartHeight - abs(it.animatedValue as Int)) / toolbarTopPartHeight.toFloat() * 1.3f)
                } else
                    fadeAlpha(abs(it.animatedValue as Int) / toolbarTopPartHeight * 1.3f)
            }
            if (snapToZero) {
                setIntValues(view.marginTop, 0)
            } else {
                setIntValues(view.marginTop, -fullHeight)
            }
        }
        return snapAnimator!!
    }


    //  In searching mode, i dont want title to reveal
    fun switchMode() {
        searchingMode = !searchingMode
    }


    fun getViews(child: AppBarLayout) {
        if (defineViewsFlag) return
        defineViewsFlag = true

        toolbarPartsHolder = child.findViewById(R.id.toolbarPartsHolder)

        toolbarTopPart = child.findViewById(R.id.toolbarTopPart)
        toolbarTitle = toolbarTopPart.findViewById(R.id.title)
        toolbarBtn = toolbarTopPart.findViewById(R.id.settingBtn)

        toolbarSearchPart = child.findViewById(R.id.toolbarSearchPart)

        toolbarTopPartHeight = toolbarTopPart.height
        toolbarSearchPartHeight = toolbarSearchPart.height
    }

}