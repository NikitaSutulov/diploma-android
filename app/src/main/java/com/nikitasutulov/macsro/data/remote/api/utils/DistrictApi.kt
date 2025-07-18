package com.nikitasutulov.macsro.data.remote.api.utils

import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DistrictApi {
    @GET("District")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<DistrictDto>>

    @GET("District/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<DistrictDto>
}