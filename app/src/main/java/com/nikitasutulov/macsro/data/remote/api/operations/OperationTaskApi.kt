package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtask.CreateOperationTaskDto
import com.nikitasutulov.macsro.data.dto.operations.operationtask.OperationTaskDto
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

interface OperationTaskApi : CrudApi<OperationTaskDto, CreateOperationTaskDto> {
    @GET("/operations/api/OperationTask")
    override suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<OperationTaskDto>>

    @POST("/operations/api/OperationTask")
    override suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateOperationTaskDto
    ): Response<OperationTaskDto>

    @PUT("/operations/api/OperationTask")
    override suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: OperationTaskDto
    ): Response<OperationTaskDto>

    @GET("/operations/api/OperationTask/{gid}")
    override suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<OperationTaskDto>

    @DELETE("/operations/api/OperationTask/{gid}")
    override suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}