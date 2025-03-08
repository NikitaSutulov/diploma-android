package com.nikitasutulov.macsro.data.remote.api

import com.nikitasutulov.macsro.data.dto.resourcemeasurementunit.CreateResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.resourcemeasurementunit.ResourceMeasurementUnitDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ResourceMeasurementUnitApi {
    @GET("/utils/api/ResourceMeasurementUnit/by-unit/{unitGid}")
    suspend fun getResourceMeasurementUnitsByUnitGID(@Path("unitGid") unitGID: String): Response<List<ResourceMeasurementUnitDto>>

    @GET("/utils/api/ResourceMeasurementUnit/by-resource/{resourceGid}")
    suspend fun getResourceMeasurementUnitsByResourceGID(@Path("resourceGid") resourceGID: String): Response<List<ResourceMeasurementUnitDto>>

    @GET("/utils/api/ResourceMeasurementUnit/{gid}")
    suspend fun getResourceMeasurementUnitByGID(@Path("gid") gid: String): Response<ResourceMeasurementUnitDto>

    @DELETE("/utils/api/ResourceMeasurementUnit/{gid}")
    suspend fun deleteResourceMeasurementUnitByGID(@Path("gid") gid: String): Response<ResponseBody>

    @POST("/utils/api/ResourceMeasurementUnit")
    suspend fun createResourceMeasurementUnit(@Body createResourceMeasurementUnitDto: CreateResourceMeasurementUnitDto): Response<ResourceMeasurementUnitDto>

    @POST("utils/api/ResourceMeasurementUnit/exists")
    suspend fun existsResourceMeasurementUnit(@Body resourceMeasurementUnitDto: ResourceMeasurementUnitDto): Response<ResponseBody>
}