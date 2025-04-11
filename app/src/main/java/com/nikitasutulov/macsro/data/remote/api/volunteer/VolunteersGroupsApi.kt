package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.CreateVolunteersGroupsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VolunteersGroupsApi {
    @GET("VolunteersGroups")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<VolunteersGroupsDto>>

    @POST("VolunteersGroups")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateVolunteersGroupsDto
    ): Response<VolunteersGroupsDto>

    @GET("VolunteersGroups/by-volunteer/{volunteerGid}")
    suspend fun getByVolunteerGID(
        @Header("Authorization") token: String,
        @Path("volunteerGid") volunteerGID: String
    ): Response<List<VolunteersGroupsDto>>

    @GET("VolunteersGroups/by-group/{groupGid}")
    suspend fun getByGroupGID(
        @Header("Authorization") token: String,
        @Path("groupGid") groupGID: String
    ): Response<List<VolunteersGroupsDto>>

    @GET("VolunteersGroups/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<VolunteersGroupsDto>

    @DELETE("VolunteersGroups/{gid}")
    suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>

    @POST("VolunteersGroups/exists")
    suspend fun exists(
        @Header("Authorization") token: String,
        @Body dto: CreateVolunteersGroupsDto
    ): Response<ResponseBody>
}
