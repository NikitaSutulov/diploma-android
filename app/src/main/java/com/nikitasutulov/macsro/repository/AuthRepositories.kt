package com.nikitasutulov.macsro.repository

import com.nikitasutulov.macsro.data.dto.auth.auth.LoginDto
import com.nikitasutulov.macsro.data.dto.auth.auth.RegisterDto
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class AuthRepository {
    private val authApi = RetrofitClient.authApi

    suspend fun registerVolunteer(registerVolunteerDto: RegisterDto) = authApi.register(registerVolunteerDto)

    suspend fun login(loginDto: LoginDto) = authApi.login(loginDto)
}

class RoleRepository {
    private val roleApi = RetrofitClient.roleApi

    suspend fun getAllRoles(token: String) = roleApi.getAllRoles(token)
}

class UserRepository {
    private val userApi = RetrofitClient.userApi

    suspend fun getAllUsers(token: String) = userApi.getAllUsers(token)

    suspend fun getUsersWithRole(token: String, roleName: String) = userApi.getUsersWithRole(token, roleName)

    suspend fun editUser(token: String, userDto: UserDto) = userApi.editUser(token, userDto)
}
