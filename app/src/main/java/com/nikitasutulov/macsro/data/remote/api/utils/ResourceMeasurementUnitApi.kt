package com.nikitasutulov.macsro.data.remote.api.utils

import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.CreateResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.ResourceMeasurementUnitDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ResourceMeasurementUnitApi {
    @GET("/utils/api/ResourceMeasurementUnit/by-unit/{unitGid}")
    suspend fun getByUnitGID(
        @Header("Authorization") token: String,
        @Path("unitGid") unitGID: String
    ): Response<List<ResourceMeasurementUnitDto>>

    @GET("/utils/api/ResourceMeasurementUnit/by-resource/{resourceGid}")
    suspend fun getByResourceGID(
        @Header("Authorization") token: String,
        @Path("resourceGid") resourceGID: String
    ): Response<List<ResourceMeasurementUnitDto>>

    @GET("/utils/api/ResourceMeasurementUnit/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResourceMeasurementUnitDto>

    @DELETE("/utils/api/ResourceMeasurementUnit/{gid}")
    suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>

    @POST("/utils/api/ResourceMeasurementUnit")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body createResourceMeasurementUnitDto: CreateResourceMeasurementUnitDto
    ): Response<ResourceMeasurementUnitDto>

    @POST("utils/api/ResourceMeasurementUnit/exists")
    suspend fun exists(
        @Header("Authorization") token: String,
        @Body resourceMeasurementUnitDto: ResourceMeasurementUnitDto
    ): Response<ResponseBody>
}