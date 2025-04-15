package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.group.CreateGroupDto
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
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

interface GroupApi {
    @GET("Group")
    suspend fun getAll(
        @Header("Authorization") token: String,
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<GroupDto>>

    @POST("Group")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body createDto: CreateGroupDto
    ): Response<GroupDto>

    @PUT("Group")
    suspend fun edit(
        @Header("Authorization") token: String,
        @Body dto: GroupDto
    ): Response<GroupDto>

    @GET("Group/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<GroupDto>

    @DELETE("Group/{gid}")
    suspend fun deleteByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<ResponseBody>
}