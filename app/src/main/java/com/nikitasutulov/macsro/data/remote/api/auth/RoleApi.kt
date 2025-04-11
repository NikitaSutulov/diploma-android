package com.nikitasutulov.macsro.data.remote.api.auth

import com.nikitasutulov.macsro.data.dto.auth.role.RoleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RoleApi {
    @GET("Role/collection")
    suspend fun getAllRoles(@Header("Authorization") token: String): Response<List<RoleDto>>
}