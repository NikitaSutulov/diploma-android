package com.nikitasutulov.macsro.viewmodel.auth.role

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.role.RoleDto
import com.nikitasutulov.macsro.repository.auth.RoleRepository
import com.nikitasutulov.macsro.util.performRequest


class RoleViewModel(private val roleRepository: RoleRepository): ViewModel() {
    private val _getAllRolesResponse = MutableLiveData<BaseResponse<List<RoleDto>>>()
    val getAllRolesResponse: LiveData<BaseResponse<List<RoleDto>>> = _getAllRolesResponse

    fun getAllRoles(token: String) {
        performRequest(
            request = { roleRepository.getAllRoles(token) },
            responseLiveData = _getAllRolesResponse
        )
    }
}