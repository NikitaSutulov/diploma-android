package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.eventtype.CreateEventTypeDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
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

interface EventTypeApi {
    @GET("EventType")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventTypeDto>>

    @POST("EventType")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateEventTypeDto
    ): Response<EventTypeDto>

    @PUT("EventType")
    suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: EventTypeDto
    ): Response<EventTypeDto>

    @GET("EventType/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<EventTypeDto>

    @DELETE("EventType/{gid}")
    suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}