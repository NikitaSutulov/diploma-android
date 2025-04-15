package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.event.CreateEventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
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

interface EventApi {
    @GET("Event")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventDto>>

    @POST("Event")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateEventDto
    ): Response<EventDto>

    @PUT("Event")
    suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: EventDto
    ): Response<EventDto>

    @GET("Event/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<EventDto>

    @DELETE("Event/{gid}")
    suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}