package ru.fefu.database.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun dateToTimestamp(date: Date?) : Long? = date?.time

    @TypeConverter
    fun fromTimestamp(value: Long?) : Date? = value?.let { Date(it) }
}