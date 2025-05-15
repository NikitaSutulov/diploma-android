package com.nikitasutulov.macsro.data.remote.api.auth

import com.nikitasutulov.macsro.data.dto.ExistsDto
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("User/check/byname/{username}")
    suspend fun getUsersWithUsername(
        @Path("username") username: String
    ): Response<ExistsDto>

    @GET("User/check/byemail/{email}")
    suspend fun getUsersWithEmail(
        @Path("email") email: String
    ): Response<ExistsDto>

    @PUT("User")
    suspend fun editUser(
        @Header("Authorization") token: String,
        userDto: UserDto
    ): Response<UserDto>
}