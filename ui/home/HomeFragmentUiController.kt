package com.example.iplayer.ui.home

import android.animation.Animator
import android.content.Context
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.RadioGroup
import com.example.iplayer.*
import com.example.iplayer.network.ITunesApi

class HomeFragmentUiController (
    context: Context,
    private val appbarBehaviour: HomeAppBarCoordinatorBehaviour
) {
    //*****************
    // SEARCH RELATED *
    //*****************
    var searchingFlag = false

    private val appbar : View
    private val nestedScrollView : View

    private val homeRecyclerView : View
    private val searchRecyclerView : View
    private val searchProgressBar : View
    private val startTyping : View
    private val nothingFound : View

    private val listOfSearchRelatedViews : List<View>

    private val homeScreenBgColor: Int by bindColor(context,R.color.homeBackgroundColor)
    private val homeScreenBgInvertedColor: Int by bindColor(context,R.color.homeBackgroundColorInverted)
    //*****************

    //*****************
    //      OTHER     *
    //*****************

    private val radioGroupSearch : RadioGroup

    init {
        val homeActivity = (context as HomeActivity)

        // SEARCH RELATED
        appbar = homeActivity.findViewById(R.id.appbar)
        nestedScrollView = homeActivity.findViewById(R.id.nesterScrollView)

        homeRecyclerView = homeActivity.findViewById(R.id.homeRecycleView)
        searchRecyclerView = homeActivity.findViewById(R.id.searchRecycleView)
        searchProgressBar = homeActivity.findViewById(R.id.searchProgressBar)
        startTyping = homeActivity.findViewById(R.id.startTyping)
        nothingFound = homeActivity.findViewById(R.id.nothingFound)

        listOfSearchRelatedViews = listOf(
            searchRecyclerView,
            searchProgressBar,
            startTyping,
            nothingFound
        )

        // OTHER
        radioGroupSearch = homeActivity.findViewById(R.id.radioGroupSearch) as RadioGroup
    }

    //***********************
    // SEARCH RELATED START *
    //***********************
    fun showSearchRecyclerView() {
        searchRecyclerView.visibility = View.VISIBLE
    }

    fun showNothingFound() {
        nothingFound.visibility = View.VISIBLE
    }

    fun initShowSearchingSetup() {
        if (searchingFlag) return
        searchingFlag = true

        initShowSearchingSetupAnimation(appbarBehaviour.animateHideTitleToolbar())

        radioGroupSearch.visibility = View.VISIBLE
        homeRecyclerView.visibility = View.GONE
        startTyping.visibility = View.VISIBLE
    }
    private fun initShowSearchingSetupAnimation(animator: Animator?) {
        val duration = animator?.duration ?: AppConstants.DEFAULT_ANIMATION_DURATION
        val colorAnimator = getValueAnimator(true, duration, DecelerateInterpolator()) {

            val color = blendColors(homeScreenBgColor, homeScreenBgInvertedColor, it)
            appbar.setBackgroundColor(color)
            nestedScrollView.setBackgroundColor(color)

        }

        animator?.start()
        colorAnimator.start()
    }

    fun hideSearchingSetup() {
        searchingFlag = false

        hideEverySearchRelatedViewBut(-1) // view with id -1 doesn't exist so its hides every search related view
        hideSearchingSetupAnimation()

        radioGroupSearch.visibility = View.GONE
        homeRecyclerView.visibility = View.VISIBLE
    }
    private fun hideSearchingSetupAnimation() {
        val duration = AppConstants.DEFAULT_ANIMATION_DURATION
        val colorAnimator = getValueAnimator(true, duration, DecelerateInterpolator()) {

            val color = blendColors(homeScreenBgInvertedColor, homeScreenBgColor, it)
            appbar.setBackgroundColor(color)
            nestedScrollView.setBackgroundColor(color)

        }

        colorAnimator.start()
    }

    fun showSearchingProgress() {
        startTyping.visibility = View.GONE
        nothingFound.visibility = View.GONE

        searchProgressBar.visibility = View.VISIBLE
    }

    fun hideSearchingProgress() {
        searchProgressBar.visibility = View.GONE
    }

    private fun hideEverySearchRelatedViewBut(viewId: Int) {
        for (sv in listOfSearchRelatedViews) {
            if (sv.id != viewId) sv.visibility = View.GONE
        }
    }
    //**********************
    //  SEARCH RELATED END *
    //**********************

    //********************************
    //  OTHER UI MANIPULATIONS START *
    //********************************

    fun setRadioButtonSelected(entity: ITunesApi.Entity) {
        when (entity) {
            ITunesApi.Entity.SONG -> radioGroupSearch.check(R.id.radioBtnSong)
            ITunesApi.Entity.ALBUM -> radioGroupSearch.check(R.id.radioBtnAlbum)
            ITunesApi.Entity.ARTIST -> radioGroupSearch.check(R.id.radioBtnArtist)
        }
    }

    //******************************
    //  OTHER UI MANIPULATIONS END *
    //******************************
}