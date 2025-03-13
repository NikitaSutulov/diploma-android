package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.eventtype.CreateEventTypeDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
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

interface EventTypeApi: CrudApi<EventTypeDto, CreateEventTypeDto> {
    @GET("/operations/api/EventType")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventTypeDto>>

    @POST("/operations/api/EventType")
    override suspend fun create(@Body createDto: CreateEventTypeDto): Response<EventTypeDto>

    @PUT("/operations/api/EventType")
    override suspend fun edit(@Body dto: EventTypeDto): Response<EventTypeDto>

    @GET("/operations/api/EventType/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<EventTypeDto>

    @DELETE("/operations/api/EventType/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}