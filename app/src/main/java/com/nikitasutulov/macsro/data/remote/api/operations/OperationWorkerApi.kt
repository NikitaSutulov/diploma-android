package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationworker.CreateOperationWorkerDto
import com.nikitasutulov.macsro.data.dto.operations.operationworker.OperationWorkerDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OperationWorkerApi {
    @GET("/operations/api/OperationWorker")
    suspend fun getAllOperationWorkers(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<OperationWorkerDto>>

    @POST("/operations/api/OperationWorker")
    suspend fun createOperationWorker(@Body createOperationWorkerDto: CreateOperationWorkerDto): Response<OperationWorkerDto>

    @PUT("/operations/api/OperationWorker")
    suspend fun editOperationWorker(@Body operationWorkerDto: OperationWorkerDto): Response<OperationWorkerDto>

    @GET("/operations/api/OperationWorker/{gid}")
    suspend fun getOperationWorkerByGID(@Path("gid") gid: String): Response<OperationWorkerDto>

    @DELETE("/operations/api/OperationWorker/{gid}")
    suspend fun deleteOperationWorkerByGID(@Path("gid") gid: String): Response<ResponseBody>
}