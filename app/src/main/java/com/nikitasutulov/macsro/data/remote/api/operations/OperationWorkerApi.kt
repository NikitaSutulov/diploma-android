package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationworker.CreateOperationWorkerDto
import com.nikitasutulov.macsro.data.dto.operations.operationworker.OperationWorkerDto
import com.nikitasutulov.macsro.data.remote.api.CrudApi
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OperationWorkerApi: CrudApi<OperationWorkerDto, CreateOperationWorkerDto> {
    @GET("/operations/api/OperationWorker")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<OperationWorkerDto>>

    @POST("/operations/api/OperationWorker")
    override suspend fun create(@Body createDto: CreateOperationWorkerDto): Response<OperationWorkerDto>

    @PUT("/operations/api/OperationWorker")
    override suspend fun edit(@Body dto: OperationWorkerDto): Response<OperationWorkerDto>

    @GET("/operations/api/OperationWorker/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<OperationWorkerDto>

    @DELETE("/operations/api/OperationWorker/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}