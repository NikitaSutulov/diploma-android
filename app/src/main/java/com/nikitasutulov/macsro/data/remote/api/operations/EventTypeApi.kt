package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.eventtype.CreateEventTypeDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EventTypeApi {
    @GET("/operations/api/EventType")
    suspend fun getAllEventTypes(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventTypeDto>>

    @POST("/operations/api/EventType")
    suspend fun createEventType(@Body createEventTypeDto: CreateEventTypeDto): Response<EventTypeDto>

    @PUT("/operations/api/EventType")
    suspend fun editEventType(@Body eventTypeDto: EventTypeDto): Response<EventTypeDto>

    @GET("/operations/api/EventType/{gid}")
    suspend fun getEventTypeByGID(@Path("gid") gid: String): Response<EventTypeDto>

    @DELETE("/operations/api/EventType/{gid}")
    suspend fun deleteEventTypeByGID(@Path("gid") gid: String): Response<ResponseBody>
}