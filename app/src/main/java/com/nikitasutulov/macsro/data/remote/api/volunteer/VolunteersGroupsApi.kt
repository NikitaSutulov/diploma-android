package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface VolunteersGroupsApi {
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
}
