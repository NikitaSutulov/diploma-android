package com.nikitasutulov.macsro.data.remote

import com.nikitasutulov.macsro.data.remote.api.AuthApi
import com.nikitasutulov.macsro.data.remote.api.RoleApi
import com.nikitasutulov.macsro.data.remote.api.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.105:5107"

    private val retrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val authApi: AuthApi by lazy {
        retrofitClient
            .build()
            .create(AuthApi::class.java)
    }

    val roleApi: RoleApi by lazy {
        retrofitClient
            .build()
            .create(RoleApi::class.java)
    }

    val userApi: UserApi by lazy {
        retrofitClient
            .build()
            .create(UserApi::class.java)
    }
}