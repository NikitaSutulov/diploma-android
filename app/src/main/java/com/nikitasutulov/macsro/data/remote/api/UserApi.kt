package com.nikitasutulov.macsro.data.remote.api

import com.nikitasutulov.macsro.data.dto.user.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("/api/User/collection")
    suspend fun getAllUsers(@Header("Authorization") token: String): Response<List<UserDto>>

    @GET("/api/User/{roleName}")
    suspend fun getUsersWithRole(@Header("Authorization") token: String, @Path("roleName") roleName: String): Response<List<UserDto>>

    @PUT("/api/User")
    suspend fun editUser(@Header("Authorization") token: String, userDto: UserDto): Response<UserDto>
}