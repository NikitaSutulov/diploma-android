package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.CreateResourcesEventDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ResourcesEventApi {
    @GET("/operations/api/ResourcesEvent")
    suspend fun getAllResourcesEvents(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<ResourcesEventDto>>

    @POST("/operations/api/ResourcesEvent")
    suspend fun createResourcesEvent(@Body createResourcesEventDto: CreateResourcesEventDto): Response<ResourcesEventDto>

    @PUT("/operations/api/ResourcesEvent")
    suspend fun editResourcesEvent(@Body resourcesEventDto: ResourcesEventDto): Response<ResourcesEventDto>

    @GET("/operations/api/ResourcesEvent/{gid}")
    suspend fun getResourcesEventByGID(@Path("gid") gid: String): Response<ResourcesEventDto>

    @DELETE("/operations/api/ResourcesEvent/{gid}")
    suspend fun deleteResourcesEventByGID(@Path("gid") gid: String): Response<ResponseBody>
}