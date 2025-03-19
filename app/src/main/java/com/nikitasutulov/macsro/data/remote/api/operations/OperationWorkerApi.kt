package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationworker.CreateOperationWorkerDto
import com.nikitasutulov.macsro.data.dto.operations.operationworker.OperationWorkerDto
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

interface OperationWorkerApi : CrudApi<OperationWorkerDto, CreateOperationWorkerDto> {
    @GET("/operations/api/OperationWorker")
    override suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<OperationWorkerDto>>

    @POST("/operations/api/OperationWorker")
    override suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateOperationWorkerDto
    ): Response<OperationWorkerDto>

    @PUT("/operations/api/OperationWorker")
    override suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: OperationWorkerDto
    ): Response<OperationWorkerDto>

    @GET("/operations/api/OperationWorker/{gid}")
    override suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<OperationWorkerDto>

    @DELETE("/operations/api/OperationWorker/{gid}")
    override suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}