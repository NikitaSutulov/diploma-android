package com.nikitasutulov.macsro.repository.auth

import com.nikitasutulov.macsro.data.remote.RetrofitClient

class RoleRepository {
    private val roleApi = RetrofitClient.roleApi

    suspend fun getAllRoles(token: String) = roleApi.getAllRoles(token)
}