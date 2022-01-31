package ru.fefu.api

import android.content.Context
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import ru.fefu.api.models.Gender
import ru.fefu.api.models.LoginData
import ru.fefu.api.models.RegistrationData
import ru.fefu.api.models.ResponseModel

interface LoginApi {
    @POST("api/auth/register")
    fun register(
        @Query("login") login: String,
        @Query("password") password: String,
        @Query("name") name: String,
        @Query("gender") gender: Int
    ): Call<ResponseModel>

    @POST("api/auth/login")
    fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): Call<ResponseModel>

    @POST("api/auth/logout")
    fun logout(@Header("Authorization") token: String = "Bearer 491|VgDKM0Kve3jd023AyoKmAdkPMqI4fvcUw7zAueFM"): Call<Unit>
}