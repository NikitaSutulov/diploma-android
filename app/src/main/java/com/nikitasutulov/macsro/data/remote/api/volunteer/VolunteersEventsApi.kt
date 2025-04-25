package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.CreateVolunteersEventsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.VolunteersEventsDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VolunteersEventsApi {
    @GET("VolunteersEvents")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<VolunteersEventsDto>>

    @POST("VolunteersEvents")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateVolunteersEventsDto
    ): Response<VolunteersEventsDto>

    @GET("VolunteersEvents/by-volunteer/{volunteerGid}")
    suspend fun getByVolunteerGID(
        @Header("Authorization") token: String,
        @Path("volunteerGid") volunteerGID: String
    ): Response<List<VolunteersEventsDto>>

    @GET("VolunteersEvents/by-event/{eventGid}")
    suspend fun getByEventGID(
        @Header("Authorization") token: String,
        @Path("eventGid") eventGID: String
    ): Response<List<VolunteersEventsDto>>

    @GET("VolunteersEvents/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<VolunteersEventsDto>

    @DELETE("VolunteersEvents/{gid}")
    suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>

    @POST("VolunteersEvents/exists")
    suspend fun exists(
        @Header("Authorization") token: String,
        @Body dto: CreateVolunteersEventsDto
    ): Response<ResponseBody>
}
