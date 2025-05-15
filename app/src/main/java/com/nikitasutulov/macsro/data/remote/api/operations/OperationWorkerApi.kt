package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationworker.OperationWorkerDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface OperationWorkerApi {
    @GET("OperationWorker/byUserGID/{userGID}")
    suspend fun getByUserGID(
        @Header("Authorization") token: String,
        @Path("userGID") userGID: String
    ): Response<OperationWorkerDto>
}