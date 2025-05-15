package com.nikitasutulov.macsro.data.remote.api.utils

import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ResourceApi {
    @GET("Resource")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<ResourceDto>>
}