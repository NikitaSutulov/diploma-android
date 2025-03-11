package com.nikitasutulov.macsro.data.dto.auth.auth

data class SuccessfulLoginResponseDto(
    val token: String,
    val expiration: String
)
