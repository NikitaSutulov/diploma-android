package com.nikitasutulov.macsro.data.dto.auth.user

import com.nikitasutulov.macsro.data.dto.auth.role.RoleDto

data class UserDto(
    val id: String,
    val name: String,
    val roles: List<RoleDto>
)