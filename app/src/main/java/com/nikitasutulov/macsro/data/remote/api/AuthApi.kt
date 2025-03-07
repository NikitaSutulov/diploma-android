package com.nikitasutulov.macsro.data.remote.api

import com.nikitasutulov.macsro.data.dto.auth.LoginDto
import com.nikitasutulov.macsro.data.dto.auth.RegisterDto
import com.nikitasutulov.macsro.data.dto.auth.SuccessfulLoginResponseDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/Authenticate/register")
    suspend fun register(@Body registerDto: RegisterDto): Response<ResponseBody>

    @POST("/api/Authenticate/login")
    suspend fun login(@Body loginDto: LoginDto): Response<SuccessfulLoginResponseDto>
}