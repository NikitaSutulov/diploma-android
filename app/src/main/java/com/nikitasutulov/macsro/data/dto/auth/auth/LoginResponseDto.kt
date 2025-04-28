package com.nikitasutulov.macsro.data.dto.auth.auth

data class LoginResponseDto(
    val authenticatorKey: String,
    val isValid: Boolean,
    val message: String
)
