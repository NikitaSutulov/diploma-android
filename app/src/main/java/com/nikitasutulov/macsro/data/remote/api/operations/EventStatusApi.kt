package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface EventStatusApi {
    @GET("EventStatus")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventStatusDto>>

    @GET("EventStatus/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<EventStatusDto>
}