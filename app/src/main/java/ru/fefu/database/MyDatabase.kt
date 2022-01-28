package ru.fefu.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Activity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun activeDao(): ActiveDao
}
