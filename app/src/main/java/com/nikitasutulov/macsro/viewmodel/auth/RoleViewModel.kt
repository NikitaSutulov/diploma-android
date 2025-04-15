package com.nikitasutulov.macsro.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.role.RoleDto
import com.nikitasutulov.macsro.repository.RoleRepository
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class RoleViewModel(private val repository: RoleRepository) : ApiClientViewModel() {
    private val _getAllRolesResponse = MutableLiveData<BaseResponse<List<RoleDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<RoleDto>>> = _getAllRolesResponse

    fun getAll(token: String) {
        performRequest(
            request = { repository.getAllRoles(token) },
            responseLiveData = _getAllRolesResponse
        )
    }
}