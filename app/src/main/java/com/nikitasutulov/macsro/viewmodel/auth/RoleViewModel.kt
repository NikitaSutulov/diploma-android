package com.nikitasutulov.macsro.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.role.RoleDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class RoleViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.roleApi

    private val _getAllRolesResponse = MutableLiveData<BaseResponse<List<RoleDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<RoleDto>>> = _getAllRolesResponse

    fun getAll(token: String) {
        performRequest(
            request = { api.getAllRoles(token) },
            responseLiveData = _getAllRolesResponse
        )
    }
}