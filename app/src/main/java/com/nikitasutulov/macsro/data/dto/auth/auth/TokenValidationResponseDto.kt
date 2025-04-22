package com.nikitasutulov.macsro.data.dto.auth.auth

import com.nikitasutulov.macsro.data.dto.auth.user.UserDto

data class TokenValidationResponseDto(
    val isValid: Boolean,
    val user: UserDto?
)
