package com.nikitasutulov.macsro.data.remote.api.volunteer

import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.CreateVolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.VolunteersDistrictsDto
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

interface VolunteersDistrictsApi : CrudApi<VolunteersDistrictsDto, CreateVolunteersDistrictsDto> {
    @GET("VolunteersDistricts")
    override suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<VolunteersDistrictsDto>>

    @POST("VolunteersDistricts")
    override suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateVolunteersDistrictsDto
    ): Response<VolunteersDistrictsDto>

    @PUT("VolunteersDistricts")
    override suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: VolunteersDistrictsDto
    ): Response<VolunteersDistrictsDto>

    @GET("VolunteersDistricts/{gid}")
    override suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<VolunteersDistrictsDto>

    @DELETE("VolunteersDistricts/{gid}")
    override suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}
