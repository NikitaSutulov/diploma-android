package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GroupApi {
    @GET("Group/{gid}")
    suspend fun getByGID(
        @Header("Authorization") token: String,
        @Path("gid") gid: String
    ): Response<GroupDto>

    @GET("Group/byEventGID/{eventGID}")
    suspend fun getByEventGID(
        @Header("Authorization") token: String,
        @Path("eventGID") eventGID: String
    ): Response<List<GroupDto>>
}