package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface OperationTaskStatusApi {
    @GET("OperationTaskStatus")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<OperationTaskStatusDto>>
}