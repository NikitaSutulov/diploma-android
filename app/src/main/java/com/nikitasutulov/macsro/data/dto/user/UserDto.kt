package com.nikitasutulov.macsro.data.dto.user

import com.nikitasutulov.macsro.data.dto.role.RoleDto

data class UserDto(
    val id: String,
    val name: String,
    val roles: List<RoleDto>
)