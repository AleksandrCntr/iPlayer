package com.example.iplayer.ui.album

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iplayer.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class AlbumActivity : AppCompatActivity(R.layout.activity_album) {

    @Inject
    lateinit var albumViewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
    }
}
