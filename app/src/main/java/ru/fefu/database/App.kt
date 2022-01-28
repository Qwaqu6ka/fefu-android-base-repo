package ru.fefu.database

import android.app.Application
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fefu.api.LoginApi

class App : Application() {

    companion object {
        lateinit var INSTANCE: App

        private lateinit var api: LoginApi
        val getApi: LoginApi
            get() = api
    }

    val db by lazy {
        Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            "my_database"
        ).allowMainThreadQueries().build()
    }

    private lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        retrofit = Retrofit.Builder()
            .baseUrl("https://fefu.t.feip.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(LoginApi::class.java)
    }
}