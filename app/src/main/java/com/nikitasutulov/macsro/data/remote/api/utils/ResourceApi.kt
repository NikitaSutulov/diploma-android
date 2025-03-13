package com.nikitasutulov.macsro.data.remote.api.utils

import com.nikitasutulov.macsro.data.dto.utils.resource.CreateResourceDto
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
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

interface ResourceApi: CrudApi<ResourceDto, CreateResourceDto> {
    @GET("/utils/api/Resource")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<ResourceDto>>

    @POST("/utils/api/Resource")
    override suspend fun create(@Body createDto: CreateResourceDto): Response<ResourceDto>

    @PUT("/utils/api/Resource")
    override suspend fun edit(@Body dto: ResourceDto): Response<ResourceDto>

    @GET("/utils/api/Resource/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<ResourceDto>

    @DELETE("/utils/api/Resource/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}