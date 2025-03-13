package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.eventstatus.CreateEventStatusDto
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
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

interface EventStatusApi: CrudApi<EventStatusDto, CreateEventStatusDto> {
    @GET("/operations/api/EventStatus")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventStatusDto>>

    @POST("/operations/api/EventStatus")
    override suspend fun create(@Body createDto: CreateEventStatusDto): Response<EventStatusDto>

    @PUT("/operations/api/EventStatus")
    override suspend fun edit(@Body dto: EventStatusDto): Response<EventStatusDto>

    @GET("/operations/api/EventStatus/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<EventStatusDto>

    @DELETE("/operations/api/EventStatus/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}