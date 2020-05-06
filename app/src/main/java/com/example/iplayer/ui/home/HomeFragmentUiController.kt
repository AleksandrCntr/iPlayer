package com.example.iplayer.ui.home

import android.animation.Animator
import android.content.Context
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.RadioGroup
import com.example.iplayer.*
import com.example.iplayer.network.ITunesApi

class HomeFragmentUiController (
    context: Context,
    private val appbarBehaviour: HomeAppBarCoordinatorBehaviour,
    private val afterHideSearchSetup : () -> Unit
) {
    //*****************
    // SEARCH RELATED *
    //*****************
    var searchingFlag = false

    private val appbar : View
    private val nestedScrollView : View

    private val searchEditText : EditText
    private val btnTextCancel : View
    private val btnSort : View

    private val homeContainer : View
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

        searchEditText = homeActivity.findViewById(R.id.searchEditText)
        btnTextCancel = homeActivity.findViewById(R.id.btnCancel)
        btnSort = homeActivity.findViewById(R.id.btnSort)

        homeContainer = homeActivity.findViewById(R.id.homeContainer)
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
        searchRecyclerView.visibility = View.GONE
        nothingFound.visibility = View.VISIBLE
    }

    fun initShowSearchingSetup() {
        if (searchingFlag) return
        searchingFlag = true

        initShowSearchingSetupAnimation(appbarBehaviour.animateHideTitleToolbar())
        appbarBehaviour.switchMode()

        radioGroupSearch.visibility = View.VISIBLE
        homeContainer.visibility = View.GONE
        startTyping.visibility = View.VISIBLE

        btnSort.visibility = View.VISIBLE
        btnTextCancel.visibility = View.VISIBLE
    }
    private fun initShowSearchingSetupAnimation(animator: Animator?) {
        val duration = animator?.duration ?: AppConstants.DEFAULT_ANIMATION_DURATION
        val colorAnimator = getValueAnimator(true, duration, DecelerateInterpolator()) {

            val color = blendColors(homeScreenBgColor, homeScreenBgInvertedColor, it)
            appbar.setBackgroundColor(color)
            nestedScrollView.setBackgroundColor(color)

            btnSort.alpha = it
        }

        animator?.start()
        colorAnimator.start()
    }
//    private fun initShowSearchViewButtonsAnimation() {
//        btnSort.al
//    }

    fun hideSearchingSetup() {
        searchingFlag = false

        hideEverySearchRelatedViewBut(-1) // view with id -1 doesn't exist so its hides every search related view
        hideSearchingSetupAnimation()
        appbarBehaviour.switchMode()

        searchEditText.text?.clear()
        searchEditText.clearFocus()

        radioGroupSearch.visibility = View.GONE
        homeContainer.visibility = View.VISIBLE

        btnSort.visibility = View.GONE
        btnTextCancel.visibility = View.GONE

        afterHideSearchSetup()
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