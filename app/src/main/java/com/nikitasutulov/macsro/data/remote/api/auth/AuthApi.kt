package com.nikitasutulov.macsro.data.remote.api.auth

import com.nikitasutulov.macsro.data.dto.auth.auth.GetTokenDto
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginDto
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginResponseDto
import com.nikitasutulov.macsro.data.dto.auth.auth.RegisterDto
import com.nikitasutulov.macsro.data.dto.auth.auth.TokenInfoDto
import com.nikitasutulov.macsro.data.dto.auth.auth.TokenValidationResponseDto
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("Authenticate/register")
    suspend fun register(@Body registerDto: RegisterDto): Response<UserDto>

    @POST("Authenticate/2fa/login")
    suspend fun login(@Body loginDto: LoginDto): Response<LoginResponseDto>

    @POST("Authenticate/2fa/gettoken")
    suspend fun getToken(@Body getTokenDto: GetTokenDto): Response<TokenInfoDto>

    @GET("Authenticate/me")
    suspend fun validateToken(@Header("Authorization") token: String): Response<TokenValidationResponseDto>
}
