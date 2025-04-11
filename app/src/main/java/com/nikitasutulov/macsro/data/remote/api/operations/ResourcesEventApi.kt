package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.CreateResourcesEventDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.data.remote.api.CrudApi
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ResourcesEventApi : CrudApi<ResourcesEventDto, CreateResourcesEventDto> {
    @GET("ResourcesEvent")
    override suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<ResourcesEventDto>>

    @POST("ResourcesEvent")
    override suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateResourcesEventDto
    ): Response<ResourcesEventDto>

    @PUT("ResourcesEvent")
    override suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: ResourcesEventDto
    ): Response<ResourcesEventDto>

    @GET("ResourcesEvent/{gid}")
    override suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResourcesEventDto>

    @DELETE("ResourcesEvent/{gid}")
    override suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}