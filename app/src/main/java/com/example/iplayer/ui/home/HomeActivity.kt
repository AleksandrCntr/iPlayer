package com.example.iplayer.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iplayer.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class HomeActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var homeViewModel: HomeViewModel

    private lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        homeFragment = supportFragmentManager.findFragmentById(R.id.homeFragment) as HomeFragment
    }

    override fun onBackPressed() {
        if ( homeFragment.uic.searchingFlag ) {
            //  todo consider moving to controller
            homeFragment.uic.hideSearchingSetup()
        } else
            super.onBackPressed()
    }
}