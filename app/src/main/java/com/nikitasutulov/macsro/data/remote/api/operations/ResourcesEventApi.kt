package com.nikitasutulov.macsro.data.remote.api.operations

import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ResourcesEventApi {
    @GET("ResourcesEvent/by-event/{eventGid}")
    suspend fun getByEventGID(
        @Header("Authorization") token: String,
        @Path("eventGid") eventGID: String
    ): Response<List<ResourcesEventDto>>
}