package com.nikitasutulov.macsro.data.remote.api.auth

import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("User/collection")
    suspend fun getAllUsers(@Header("Authorization") token: String): Response<List<UserDto>>

    @GET("User/byname/{username}")
    suspend fun getUsersWithUsername(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Response<ResponseBody>

    @GET("User/byemail/{email}")
    suspend fun getUsersWithEmail(
        @Header("Authorization") token: String,
        @Path("email") email: String
    ): Response<ResponseBody>

    @GET("User/{roleName}")
    suspend fun getUsersWithRole(
        @Header("Authorization") token: String,
        @Path("roleName") roleName: String
    ): Response<List<UserDto>>

    @PUT("User")
    suspend fun editUser(
        @Header("Authorization") token: String,
        userDto: UserDto
    ): Response<UserDto>
}