package com.nikitasutulov.macsro.repository

import com.nikitasutulov.macsro.data.dto.auth.LoginDto
import com.nikitasutulov.macsro.data.dto.auth.RegisterDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class AuthRepository {
    private val authApi = RetrofitClient.authApi

    suspend fun registerVolunteer(registerVolunteerDto: RegisterDto) = authApi.register(registerVolunteerDto)

    suspend fun login(loginDto: LoginDto) = authApi.login(loginDto)
}