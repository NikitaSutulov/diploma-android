package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.eventstatus.CreateEventStatusDto
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EventStatusApi {
    @GET("/operations/api/EventStatus")
    suspend fun getAllEventStatuses(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventStatusDto>>

    @POST("/operations/api/EventStatus")
    suspend fun createEventStatus(@Body createEventStatusDto: CreateEventStatusDto): Response<EventStatusDto>

    @PUT("/operations/api/EventStatus")
    suspend fun editEventStatus(@Body eventStatusDto: EventStatusDto): Response<EventStatusDto>

    @GET("/operations/api/EventStatus/{gid}")
    suspend fun getEventStatusByGID(@Path("gid") gid: String): Response<EventStatusDto>

    @DELETE("/operations/api/EventStatus/{gid}")
    suspend fun deleteEventStatusByGID(@Path("gid") gid: String): Response<ResponseBody>
}