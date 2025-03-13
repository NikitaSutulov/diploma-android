package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.event.CreateEventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
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

interface EventApi: CrudApi<EventDto, CreateEventDto> {
    @GET("/operations/api/Event")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<EventDto>>

    @POST("/operations/api/Event")
    override suspend fun create(@Body createDto: CreateEventDto): Response<EventDto>

    @PUT("/operations/api/Event")
    override suspend fun edit(@Body dto: EventDto): Response<EventDto>

    @GET("/operations/api/Event/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<EventDto>

    @DELETE("/operations/api/Event/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}