package ru.fefu.database

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class CoordConverter {
    var gson = GsonBuilder().setPrettyPrinting().create()
    val itemType = object : TypeToken<List<Point>>() {}.type

    @TypeConverter
    fun ListToJson(list: List<Point>): String = gson.toJson(list)

    @TypeConverter
    fun JsonToList(json: String): List<Point> =
        gson.fromJson<List<Point>>(json, itemType)
}