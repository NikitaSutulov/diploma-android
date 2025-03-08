package com.nikitasutulov.macsro.data.remote.api

import com.nikitasutulov.macsro.data.dto.measurementunit.CreateMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.measurementunit.MeasurementUnitDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MeasurementUnitApi {
    @GET("/utils/api/MeasurementUnit")
    suspend fun getAllMeasurementUnits(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<MeasurementUnitDto>>

    @POST("/utils/api/MeasurementUnit")
    suspend fun createMeasurementUnit(@Body createMeasurementUnitDto: CreateMeasurementUnitDto): Response<MeasurementUnitDto>

    @PUT("/utils/api/MeasurementUnit")
    suspend fun editMeasurementUnit(@Body measurementUnitDto: MeasurementUnitDto): Response<MeasurementUnitDto>

    @GET("/utils/api/MeasurementUnit/{gid}")
    suspend fun getMeasurementUnitByGID(@Path("gid") gid: String): Response<MeasurementUnitDto>

    @DELETE("/utils/api/MeasurementUnit/{gid}")
    suspend fun deleteMeasurementUnitByGID(@Path("gid") gid: String): Response<ResponseBody>
}