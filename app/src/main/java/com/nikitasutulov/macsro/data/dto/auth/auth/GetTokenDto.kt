package com.nikitasutulov.macsro.data.dto.auth.auth

data class GetTokenDto(
    val code: String,
    val username: String
)
