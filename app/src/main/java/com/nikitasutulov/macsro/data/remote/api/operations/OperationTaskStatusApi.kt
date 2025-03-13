package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.CreateOperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
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

interface OperationTaskStatusApi: CrudApi<OperationTaskStatusDto, CreateOperationTaskStatusDto> {
    @GET("/operations/api/OperationTaskStatus")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<OperationTaskStatusDto>>

    @POST("/operations/api/OperationTaskStatus")
    override suspend fun create(@Body createDto: CreateOperationTaskStatusDto): Response<OperationTaskStatusDto>

    @PUT("/operations/api/OperationTaskStatus")
    override suspend fun edit(@Body dto: OperationTaskStatusDto): Response<OperationTaskStatusDto>

    @GET("/operations/api/OperationTaskStatus/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<OperationTaskStatusDto>

    @DELETE("/operations/api/OperationTaskStatus/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}