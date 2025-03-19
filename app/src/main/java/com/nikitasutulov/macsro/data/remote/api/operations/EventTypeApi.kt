package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.eventtype.CreateEventTypeDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
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

interface EventTypeApi : CrudApi<EventTypeDto, CreateEventTypeDto> {
    @GET("/operations/api/EventType")
    override suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventTypeDto>>

    @POST("/operations/api/EventType")
    override suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateEventTypeDto
    ): Response<EventTypeDto>

    @PUT("/operations/api/EventType")
    override suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: EventTypeDto
    ): Response<EventTypeDto>

    @GET("/operations/api/EventType/{gid}")
    override suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<EventTypeDto>

    @DELETE("/operations/api/EventType/{gid}")
    override suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}