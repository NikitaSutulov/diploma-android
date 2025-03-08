package com.nikitasutulov.macsro.data.remote.api

import com.nikitasutulov.macsro.data.dto.resource.CreateResourceDto
import com.nikitasutulov.macsro.data.dto.resource.ResourceDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ResourceApi {
    @GET("/utils/api/Resource")
    suspend fun getAllResources(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<ResourceDto>>

    @POST("/utils/api/Resource")
    suspend fun createResource(@Body createResourceDto: CreateResourceDto): Response<ResourceDto>

    @PUT("/utils/api/Resource")
    suspend fun editResource(@Body resourceDto: ResourceDto): Response<ResourceDto>

    @GET("/utils/api/Resource/{gid}")
    suspend fun getResourceByGID(@Path("gid") gid: String): Response<ResourceDto>

    @DELETE("/utils/api/Resource/{gid}")
    suspend fun deleteResourceByGID(@Path("gid") gid: String): Response<ResponseBody>
}