package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.group.CreateGroupDto
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupApi {
    @GET("/operations/api/Group")
    suspend fun getAllGroups(
        @Query("PageNumber") pageNumber: Int?,
        @Query("PageSize") pageSize: Int?
    ): Response<List<GroupDto>>

    @POST("/operations/api/Group")
    suspend fun createGroup(@Body createGroupDto: CreateGroupDto): Response<GroupDto>

    @PUT("/operations/api/Group")
    suspend fun editGroup(@Body groupDto: GroupDto): Response<GroupDto>

    @GET("/operations/api/Group/{gid}")
    suspend fun getGroupByGID(@Path("gid") gid: String): Response<GroupDto>

    @DELETE("/operations/api/Group/{gid}")
    suspend fun deleteGroupByGID(@Path("gid") gid: String): Response<ResponseBody>
}