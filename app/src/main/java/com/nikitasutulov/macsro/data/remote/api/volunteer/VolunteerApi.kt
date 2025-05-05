package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface VolunteerApi {
    @GET("Volunteer")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?,
        @Query("SortBy") sortBy: String,
        @Query("IsDescending") isDescending: Boolean
    ): Response<List<VolunteerDto>>

    @POST("Volunteer")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateVolunteerDto
    ): Response<VolunteerDto>

    @PUT("Volunteer")
    suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: VolunteerDto
    ): Response<VolunteerDto>

    @GET("Volunteer/by-group/{groupGID}")
    suspend fun getByGroupGID(
        @Header("Authorization") token: String,
        @Path("groupGID") groupGID: String
    ): Response<List<VolunteerDto>>

    @GET("Volunteer/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<VolunteerDto>

    @GET("Volunteer/byUserGID/{userGID}")
    suspend fun getByUserGID(
        @Header("Authorization") token: String,
        @Path("userGID") userGID: String
    ): Response<VolunteerDto>

    @DELETE("Volunteer/{gid}")
    suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}
