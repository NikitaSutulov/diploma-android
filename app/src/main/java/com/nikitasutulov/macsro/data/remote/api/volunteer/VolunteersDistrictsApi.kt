package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.CreateVolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.VolunteersDistrictsDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface VolunteersDistrictsApi {

    @POST("VolunteersDistricts")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateVolunteersDistrictsDto
    ): Response<VolunteersDistrictsDto>

    @GET("VolunteersDistricts/by-volunteer/{volunteerGid}")
    suspend fun getByVolunteerGID(
        @Header("Authorization") token: String,
        @Path("volunteerGid") volunteerGID: String
    ): Response<List<VolunteersDistrictsDto>>

    @DELETE("VolunteersDistricts/{gid}")
    suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}
