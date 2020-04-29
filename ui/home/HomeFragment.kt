package com.example.iplayer.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iplayer.AppConstants
import com.example.iplayer.R
import com.example.iplayer.bindDimen
import com.example.iplayer.data.Album
import com.example.iplayer.data.Artist
import com.example.iplayer.data.Song
import com.example.iplayer.network.ITunesApi
import com.example.iplayer.screenWidth
import com.example.iplayer.ui.home.adapters.AlbumListAdapter
import com.example.iplayer.ui.home.adapters.AlbumViewPagerAdapter
import com.example.iplayer.ui.home.adapters.SimpleMockRecyclerAdapter
import com.example.iplayer.ui.home.adapters.SongListAdapter
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit
import com.example.iplayer.database.Album as RoomAlbum

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val homeViewModel : HomeViewModel by activityViewModels()

    private lateinit var searchObservable : Observable<String>
    private val coordinatorBehaviour = HomeAppBarCoordinatorBehaviour()

    private val viewPagerPageMargin by bindDimen(R.dimen.view_pager_item_margin_between)
    private val viewPagerPageWidth by bindDimen(R.dimen.view_pager_item_height)
    private val viewPagerPageWidthF : Float by lazy {
        viewPagerPageWidth / requireContext().screenWidth
    }

    //  UI Home Fragment Controller controls all UI hiding, showing and animation
    lateinit var uic : HomeFragmentUiController
            private set

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //  Setting up coordinator behaviour
        (appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = coordinatorBehaviour

        //  Initializing adapters
        homeRecycleView.adapter = SimpleMockRecyclerAdapter(R.layout.item_single_album_somg)
        homeRecycleView.layoutManager = LinearLayoutManager(activity)
        searchRecycleView.layoutManager = LinearLayoutManager(activity)

        //  SETUP view pager to display most recently viewed albums from ROOM
        viewHistoryViewPager.pageMargin = viewPagerPageMargin.toInt()

        //    On switch of a radio group we need to ask observer of live data for latest value
        radioGroupRecent.setOnCheckedChangeListener { _, checkedId ->
            updateViewPager(checkedId)
        }

        //    On start we take LiveData values, but later if user is searching and doesn't see viewpager
        //we don't bother to populate it with new values needlessly, instead we ask for latest when we get
        //out from searching mode
        homeViewModel.mostViewedAlbums.observe(viewLifecycleOwner, Observer {
                mostViewedAlbumsList ->
            if (!uic.searchingFlag && radioBtnMostViewed.isChecked && mostViewedAlbumsList != null && mostViewedAlbumsList.isNotEmpty()) {
                populateHistoryViewPager(mostViewedAlbumsList)
            }
        })
        homeViewModel.recentlyViewedAlbums.observe(viewLifecycleOwner, Observer {
                recentlyViewedAlbumsList ->
            if (!uic.searchingFlag && radioBtnRecent.isChecked && recentlyViewedAlbumsList != null && recentlyViewedAlbumsList.isNotEmpty()) {
                populateHistoryViewPager(recentlyViewedAlbumsList)
            }
        })

        //  RxJava Setup for Search Edit View with debouncing and other modifications
        setupSearchEditViewObservable()

        //  UiController with a custom lambda to populate latest live data
        uic = HomeFragmentUiController(requireActivity(), coordinatorBehaviour) {
            updateViewPager(radioGroupRecent.checkedRadioButtonId)
        }

        //  On Layout Change select previously selected search type
        homeViewModel.getCurrentSearchTypeValue()?.let {
            uic.setRadioButtonSelected(it)
        }

        //  Search setup is displayed over Explore and Suggestions views
        searchView.setOnFocusChangeListener {
                _, _ ->
            uic.initShowSearchingSetup()
        }

        //  On Radio button group checked we change the entities type that we search for
        radioGroupSearch.setOnCheckedChangeListener {
                _, checkedId ->
            when(checkedId) {
                R.id.radioBtnSong -> homeViewModel.setMusicSearchType(ITunesApi.Entity.SONG)
                R.id.radioBtnAlbum -> homeViewModel.setMusicSearchType(ITunesApi.Entity.ALBUM)
                R.id.radioBtnArtist -> homeViewModel.setMusicSearchType(ITunesApi.Entity.ARTIST)
            }
            searchView.text.toString().trim().let {
                if (it.isNotEmpty()) homeViewModel.searchITunes(it)
            }
        }

        //  On search result received we populate specific recycler view or
        //show that nothing was found view
        homeViewModel.musicSearchResult.observe(viewLifecycleOwner, Observer {
                resultList ->

            uic.hideSearchingProgress()

            if (resultList.isEmpty())
                uic.showNothingFound()
            else {
                when (resultList.first()) {
                    is Song -> { searchRecycleView.adapter = SongListAdapter(resultList as List<Song>) { } }
                    is Album -> { searchRecycleView.adapter = AlbumListAdapter(resultList as List<Album>) { homeViewModel.insertJustViewedAlbum(it) } }
                    is Artist -> { searchRecycleView.adapter = SongListAdapter(resultList as List<Song>) { } }
                }
                uic.showSearchRecyclerView()
            }

        })
    }


    private fun updateViewPager(checkedId: Int) {
        when (checkedId) {
            R.id.radioBtnRecent -> {
                homeViewModel.getRecentlyViewedAlbumsValue()?.also {
                    populateHistoryViewPager(it)
                }
            }
            R.id.radioBtnMostViewed -> {
                homeViewModel.getMostViewedAlbumsValue()?.also {
                    populateHistoryViewPager(it)
                }
            }
        }
    }

    private fun populateHistoryViewPager(albumList: List<RoomAlbum>) {
        viewHistoryViewPager.adapter = AlbumViewPagerAdapter(
            albumList.map { Album(it) },
            viewPagerPageWidthF
        ) {  }
    }


    //  Setting up RxJava for EditText search View
    @SuppressLint("CheckResult")
    private fun setupSearchEditViewObservable() {
        searchObservable = Observable.create(ObservableOnSubscribe<String> { subscriber ->
                searchView.doAfterTextChanged { subscriber.onNext(it.toString()) }
            })

        searchObservable
            .map { it.trim() }
            .debounce(AppConstants.SEARCH_QUERY_DEBOUNCE, TimeUnit.MILLISECONDS)
            .filter { it.isNotBlank() }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                activity?.runOnUiThread { uic.showSearchingProgress() }
                homeViewModel.searchITunes(it)
            }
    }
}
