package com.example.iplayer.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iplayer.R
import com.example.iplayer.data.Album
import com.example.iplayer.getReleaseDateYear
import com.squareup.picasso.Picasso
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.fragment_album.*

class AlbumFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumFragment()
    }

    private val albumViewModel : AlbumViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.intent?.getParcelableExtra<Album>("album")?.let {
                album ->

            Picasso.get().load(album.artworkUrl100).into(artwork)
            albumViewModel.searchAllTracksById(album.collectionId)
            artwork.post {
                appbarBg.background = artwork.drawable
                appbarBg.post {
                    Blurry.with(context)
                        .async()
                        .sampling(8)
                        .capture(appbarBg)
                        .into(appbarBg)
                }
            }
            albumName.text = album.collectionName
            bandName.text = album.artistName
            genreAndYear.text = "${album.primaryGenreName} • ${album.getReleaseDateYear()}"
        }

        albumRecyclerView.layoutManager = LinearLayoutManager(activity)

        albumViewModel.allAlbumTracks.observe(viewLifecycleOwner, Observer {
                songsList ->
            albumRecyclerView.adapter = SongListAdapter(songsList) {  }
        })

    }
}
