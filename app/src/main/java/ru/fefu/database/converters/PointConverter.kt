package ru.fefu.database.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ru.fefu.database.Point

class PointConverter {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    @TypeConverter
    fun pointToJson(point: Point): String = gson.toJson(point)

    @TypeConverter
    fun jsonToPoint(json: String): Point =
        gson.fromJson(json, Point::class.java)
}