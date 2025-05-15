package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtask.OperationTaskDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface OperationTaskApi {
    @GET("OperationTask/byGroupGID/{groupGID}")
    suspend fun getByGroupGID(
        @Header("Authorization") token: String,
        @Path("groupGID") groupGID: String
    ): Response<List<OperationTaskDto>>
}