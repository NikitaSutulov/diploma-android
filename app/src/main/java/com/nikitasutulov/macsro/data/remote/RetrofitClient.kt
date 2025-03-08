package com.nikitasutulov.macsro.data.remote

import com.nikitasutulov.macsro.data.remote.api.AuthApi
import com.nikitasutulov.macsro.data.remote.api.DistrictApi
import com.nikitasutulov.macsro.data.remote.api.MeasurementUnitApi
import com.nikitasutulov.macsro.data.remote.api.ResourceApi
import com.nikitasutulov.macsro.data.remote.api.ResourceMeasurementUnitApi
import com.nikitasutulov.macsro.data.remote.api.RoleApi
import com.nikitasutulov.macsro.data.remote.api.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val AUTH_BASE_URL = "http://192.168.0.105:5107"
    private const val UTILS_BASE_URL = "http://192.168.0.105:5031"

    private val authRetrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    private val utilsRetrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(UTILS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val authApi: AuthApi by lazy {
        authRetrofitClient
            .build()
            .create(AuthApi::class.java)
    }

    val roleApi: RoleApi by lazy {
        authRetrofitClient
            .build()
            .create(RoleApi::class.java)
    }

    val userApi: UserApi by lazy {
        authRetrofitClient
            .build()
            .create(UserApi::class.java)
    }

    val districtApi: DistrictApi by lazy {
        utilsRetrofitClient
            .build()
            .create(DistrictApi::class.java)
    }

    val measurementUnitApi: MeasurementUnitApi by lazy {
        utilsRetrofitClient
            .build()
            .create(MeasurementUnitApi::class.java)
    }

    val resourceApi: ResourceApi by lazy {
        utilsRetrofitClient
            .build()
            .create(ResourceApi::class.java)
    }

    val resourceMeasurementUnitApi: ResourceMeasurementUnitApi by lazy {
        utilsRetrofitClient
            .build()
            .create(ResourceMeasurementUnitApi::class.java)
    }
}