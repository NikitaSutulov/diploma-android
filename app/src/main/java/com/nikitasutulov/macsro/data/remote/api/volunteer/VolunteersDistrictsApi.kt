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
import retrofit2.http.Query

interface VolunteersDistrictsApi {
    @GET("VolunteersDistricts")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<VolunteersDistrictsDto>>

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

    @GET("VolunteersDistricts/by-district/{districtGid}")
    suspend fun getByDistrictGID(
        @Header("Authorization") token: String,
        @Path("districtGid") districtGID: String
    ): Response<List<VolunteersDistrictsDto>>

    @GET("VolunteersDistricts/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<VolunteersDistrictsDto>

    @DELETE("VolunteersDistricts/{gid}")
    suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>

    @POST("VolunteersDistricts/exists")
    suspend fun exists(
        @Header("Authorization") token: String,
        @Body dto: CreateVolunteersDistrictsDto
    ): Response<ResponseBody>
}
