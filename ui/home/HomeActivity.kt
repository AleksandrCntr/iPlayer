package com.example.iplayer.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iplayer.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class HomeActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
    }
}