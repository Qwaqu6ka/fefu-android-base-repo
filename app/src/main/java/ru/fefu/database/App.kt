package ru.fefu.database

import android.app.Application
import androidx.room.Room

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    val db by lazy {
        Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            "my_database"
        ).allowMainThreadQueries().build()
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }
}