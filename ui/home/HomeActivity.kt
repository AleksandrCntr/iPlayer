package com.example.iplayer.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iplayer.App
import com.example.iplayer.R
import com.example.iplayer.network.ITunesApi
import javax.inject.Inject

class HomeActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var iTunesApi: ITunesApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)


    }
}