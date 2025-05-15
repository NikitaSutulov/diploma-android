package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface EventTypeApi {
    @GET("EventType")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventTypeDto>>

    @GET("EventType/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<EventTypeDto>
}