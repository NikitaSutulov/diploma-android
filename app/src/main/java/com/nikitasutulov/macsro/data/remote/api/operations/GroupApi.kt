package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.group.CreateGroupDto
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
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

interface GroupApi: CrudApi<GroupDto, CreateGroupDto> {
    @GET("/operations/api/Group")
    override suspend fun getAll(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<GroupDto>>

    @POST("/operations/api/Group")
    override suspend fun create(@Body createDto: CreateGroupDto): Response<GroupDto>

    @PUT("/operations/api/Group")
    override suspend fun edit(@Body dto: GroupDto): Response<GroupDto>

    @GET("/operations/api/Group/{gid}")
    override suspend fun getByGID(@Path("gid") gid: String): Response<GroupDto>

    @DELETE("/operations/api/Group/{gid}")
    override suspend fun deleteByGID(@Path("gid") gid: String): Response<ResponseBody>
}