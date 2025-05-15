package com.nikitasutulov.macsro.data.remote.api.utils

import com.nikitasutulov.macsro.data.dto.utils.measurementunit.MeasurementUnitDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MeasurementUnitApi {
    @GET("MeasurementUnit")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<MeasurementUnitDto>>
}