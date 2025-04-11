package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.CreateVolunteersGroupsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import com.nikitasutulov.macsro.data.remote.api.CrudApi
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

interface VolunteersGroupsApi : CrudApi<VolunteersGroupsDto, CreateVolunteersGroupsDto> {
    @GET("VolunteersGroups")
    override suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<VolunteersGroupsDto>>

    @POST("VolunteersGroups")
    override suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateVolunteersGroupsDto
    ): Response<VolunteersGroupsDto>

    @PUT("VolunteersGroups")
    override suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: VolunteersGroupsDto
    ): Response<VolunteersGroupsDto>

    @GET("VolunteersGroups/{gid}")
    override suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<VolunteersGroupsDto>

    @DELETE("VolunteersGroups/{gid}")
    override suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}
