package ru.fefu.database.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ru.fefu.database.Point
import java.lang.reflect.Type
import java.util.*


class CoordConverter {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    @TypeConverter
    fun toJson(someObjects: List<Point?>?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun fromJson(json: String?): List<Point?>? {
        if (json == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Point>>() {}.type
        return gson.fromJson<List<Point>>(json, listType)
    }
}