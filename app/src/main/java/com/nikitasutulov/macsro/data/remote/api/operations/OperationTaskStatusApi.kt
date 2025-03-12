package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.CreateOperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OperationTaskStatusApi {
    @GET("/operations/api/OperationTaskStatus")
    suspend fun getAllOperationTaskStatuses(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<OperationTaskStatusDto>>

    @POST("/operations/api/OperationTaskStatus")
    suspend fun createOperationTaskStatus(@Body createOperationTaskStatusDto: CreateOperationTaskStatusDto): Response<OperationTaskStatusDto>

    @PUT("/operations/api/OperationTaskStatus")
    suspend fun editOperationTaskStatus(@Body operationTaskStatusDto: OperationTaskStatusDto): Response<OperationTaskStatusDto>

    @GET("/operations/api/OperationTaskStatus/{gid}")
    suspend fun getOperationTaskStatusByGID(@Path("gid") gid: String): Response<OperationTaskStatusDto>

    @DELETE("/operations/api/OperationTaskStatus/{gid}")
    suspend fun deleteOperationTaskStatusByGID(@Path("gid") gid: String): Response<ResponseBody>
}