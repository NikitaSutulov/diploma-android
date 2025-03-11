package com.nikitasutulov.macsro.repository.auth

import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class UserRepository {
    private val userApi = RetrofitClient.userApi

    suspend fun getAllUsers(token: String) = userApi.getAllUsers(token)

    suspend fun getUsersWithRole(token: String, roleName: String) = userApi.getUsersWithRole(token, roleName)

    suspend fun editUser(token: String, userDto: UserDto) = userApi.editUser(token, userDto)
}