package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.CreateVolunteersEventsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.VolunteersEventsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface VolunteersEventsApi {
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
}
