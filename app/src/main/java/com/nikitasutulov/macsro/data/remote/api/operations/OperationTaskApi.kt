package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtask.CreateOperationTaskDto
import com.nikitasutulov.macsro.data.dto.operations.operationtask.OperationTaskDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OperationTaskApi {
    @GET("/operations/api/OperationTask")
    suspend fun getAllOperationTasks(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<OperationTaskDto>>

    @POST("/operations/api/OperationTask")
    suspend fun createOperationTask(@Body createOperationTaskDto: CreateOperationTaskDto): Response<OperationTaskDto>

    @PUT("/operations/api/OperationTask")
    suspend fun editOperationTask(@Body operationTaskDto: OperationTaskDto): Response<OperationTaskDto>

    @GET("/operations/api/OperationTask/{gid}")
    suspend fun getOperationTaskByGID(@Path("gid") gid: String): Response<OperationTaskDto>

    @DELETE("/operations/api/OperationTask/{gid}")
    suspend fun deleteOperationTaskByGID(@Path("gid") gid: String): Response<ResponseBody>
}