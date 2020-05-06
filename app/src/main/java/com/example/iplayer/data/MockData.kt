package com.example.iplayer.data

import java.text.SimpleDateFormat
import java.util.*

class MockAlbumListGenreRockSuggestion
{
    val albums: List<Album>

    init {
        albums = listOf(
            Album(
                "Led Zeppelin", "Mothership (Remastered)", 1052497413,
                "https://is1-ssl.mzstatic.com/image/thumb/Music62/v4/14/bc/e7/14bce756-cb44-d4b3-ffee-762d25d1ab88/source/100x100bb.jpg",
                24, "USA", "2015-11-06T08:00:00Z".parseSimple(), "Rock"
            ),
            Album(
                "AC/DC", "Back In Black", 574050396,
                "https://is4-ssl.mzstatic.com/image/thumb/Music/v4/73/55/ee/7355ee91-1b7e-ab3c-7117-dd4aaa1cbb75/source/100x100bb.jpg",
                11, "USA", "2012-11-19T08:00:00Z".parseSimple(), "Hard Rock"
            ),
            Album(
                "AC/DC", "Rock or Bust", 923586222,
                "https://is3-ssl.mzstatic.com/image/thumb/Music3/v4/ae/9c/4a/ae9c4afd-a533-c4fe-62bc-3c487486566a/source/100x100bb.jpg",
                12, "USA", "2014-11-28T08:00:00Z".parseSimple(), "Hard Rock"
            ),
            Album(
                "Red Hot Chili Peppers", "By the Way", 945578420,
                "https://is3-ssl.mzstatic.com/image/thumb/Music3/v4/12/63/c7/1263c7c3-6067-038a-20b1-7af875a92d5c/source/100x100bb.jpg",
                16, "USA", "2002-06-25T07:00:00Z".parseSimple(), "Alternative"
            ),
            Album(
                "Sum 41", "All the Good Shit - 14 Solid Gold Hits (2000-2008)", 945571209,
                "https://is4-ssl.mzstatic.com/image/thumb/Music113/v4/24/fd/c0/24fdc046-d3a3-7fe0-715a-94373c705de4/source/100x100bb.jpg",
                15, "USA", "2008-11-26T08:00:00Z".parseSimple(), "Alternative"
            ),
            Album(
                "LINKIN PARK", "A Thousand Suns (Deluxe Version)", 590434066,
                "https://is2-ssl.mzstatic.com/image/thumb/Music/v4/6e/96/34/6e963475-af21-e1b8-f2c4-54983f553e4a/source/100x100bb.jpg",
                18, "USA", "2010-09-13T07:00:00Z".parseSimple(), "Hard Rock"
            )
        )
    }

    fun String.parseSimple() : Date {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return Date(format.parse(this).time)
    }
}