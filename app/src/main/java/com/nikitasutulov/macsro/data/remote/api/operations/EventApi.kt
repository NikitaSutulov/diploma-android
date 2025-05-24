package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventPaginationQueryDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventsListResultDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {
    @GET("Event")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventDto>>

    @GET("Event/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<EventDto>

    @POST("Event/sort")
    suspend fun getSorted(
        @Header("Authorization") token: String,
        @Body paginationQueryDto: EventPaginationQueryDto
    ): Response<EventsListResultDto>
}
