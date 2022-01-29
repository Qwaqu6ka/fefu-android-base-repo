package ru.fefu.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.fefu.database.converters.CoordConverter
import ru.fefu.database.converters.DateConverter
import ru.fefu.database.converters.PointConverter

@Database(entities = [Activity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, CoordConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun activeDao(): ActiveDao
}
