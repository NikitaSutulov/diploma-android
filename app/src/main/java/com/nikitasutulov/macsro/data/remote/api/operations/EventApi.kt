package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.event.CreateEventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {
    @GET("/operations/api/Event")
    suspend fun getAllEvents(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventDto>>

    @POST("/operations/api/Event")
    suspend fun createEvent(@Body createEventDto: CreateEventDto): Response<EventDto>

    @PUT("/operations/api/Event")
    suspend fun editEvent(@Body eventDto: EventDto): Response<EventDto>

    @GET("/operations/api/Event/{gid}")
    suspend fun getEventByGID(@Path("gid") gid: String): Response<EventDto>

    @DELETE("/operations/api/Event/{gid}")
    suspend fun deleteEventByGID(@Path("gid") gid: String): Response<ResponseBody>
}