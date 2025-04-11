package com.nikitasutulov.macsro.data.remote.api.utils

import com.nikitasutulov.macsro.data.dto.utils.district.CreateDistrictDto
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
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

interface DistrictApi : CrudApi<DistrictDto, CreateDistrictDto> {
    @GET("District")
    override suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<DistrictDto>>

    @POST("District")
    override suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateDistrictDto
    ): Response<DistrictDto>

    @PUT("District")
    override suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: DistrictDto
    ): Response<DistrictDto>

    @GET("District/{gid}")
    override suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<DistrictDto>

    @DELETE("District/{gid}")
    override suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}