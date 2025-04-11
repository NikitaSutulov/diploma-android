package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.CreateOperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
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

interface OperationTaskStatusApi : CrudApi<OperationTaskStatusDto, CreateOperationTaskStatusDto> {
    @GET("OperationTaskStatus")
    override suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<OperationTaskStatusDto>>

    @POST("OperationTaskStatus")
    override suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateOperationTaskStatusDto
    ): Response<OperationTaskStatusDto>

    @PUT("OperationTaskStatus")
    override suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: OperationTaskStatusDto
    ): Response<OperationTaskStatusDto>

    @GET("OperationTaskStatus/{gid}")
    override suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<OperationTaskStatusDto>

    @DELETE("OperationTaskStatus/{gid}")
    override suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}