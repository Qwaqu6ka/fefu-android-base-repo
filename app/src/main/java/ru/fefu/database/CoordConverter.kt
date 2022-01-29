package ru.fefu.database

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class CoordConverter {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val itemType = object : TypeToken<List<Point>>() {}.type

    @TypeConverter
    fun listToJson(list: List<Point>): String = gson.toJson(list)

    @TypeConverter
    fun jsonToList(json: String): List<Point> =
        gson.fromJson<List<Point>>(json, itemType)
}