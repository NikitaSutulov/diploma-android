package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.request.CreateRequestDto
import com.nikitasutulov.macsro.data.dto.operations.request.RequestDto
import com.nikitasutulov.macsro.data.dto.operations.request.RequestPaginationQueryDto
import com.nikitasutulov.macsro.data.dto.operations.request.RequestsListResultDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RequestApi {
    @POST("Message/get")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Body requestPaginationQueryDto: RequestPaginationQueryDto
    ): Response<RequestsListResultDto>

    @POST("Message")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateRequestDto
    ): Response<RequestDto>

    @PUT("Message/read/{gid}")
    suspend fun read(
        @Header("Authorization") token: String,
        @Path("gid") requestGID: String
    ): Response<ResponseBody>
}