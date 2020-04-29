package com.example.iplayer.database

import androidx.room.TypeConverter
import java.util.*

class TimeConverter {

    @TypeConverter
    fun fromTimestamp(stamp: Long?): Date =
        if (stamp != null) Date(stamp) else Date(0)

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long = date?.time ?: 0

}