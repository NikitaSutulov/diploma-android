package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.CreateResourcesEventDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.data.remote.api.CrudApi
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ResourcesEventApi: CrudApi<ResourcesEventDto, CreateResourcesEventDto> {
    @GET("/operations/api/ResourcesEvent")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<ResourcesEventDto>>

    @POST("/operations/api/ResourcesEvent")
    override suspend fun create(@Body createDto: CreateResourcesEventDto): Response<ResourcesEventDto>

    @PUT("/operations/api/ResourcesEvent")
    override suspend fun edit(@Body dto: ResourcesEventDto): Response<ResourcesEventDto>

    @GET("/operations/api/ResourcesEvent/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<ResourcesEventDto>

    @DELETE("/operations/api/ResourcesEvent/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}