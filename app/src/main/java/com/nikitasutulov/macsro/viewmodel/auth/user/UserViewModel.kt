package com.nikitasutulov.macsro.viewmodel.auth.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.repository.UserRepository
import com.nikitasutulov.macsro.util.performRequest

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _getAllUsersResponse = MutableLiveData<BaseResponse<List<UserDto>>>()
    val getAllUsersResponse: LiveData<BaseResponse<List<UserDto>>> = _getAllUsersResponse

    private val _getUsersWithRoleResponse = MutableLiveData<BaseResponse<List<UserDto>>>()
    val getUsersWithRoleResponse: LiveData<BaseResponse<List<UserDto>>> = _getUsersWithRoleResponse

    private val _editUserResponse = MutableLiveData<BaseResponse<UserDto>>()
    val editUserResponse: LiveData<BaseResponse<UserDto>> = _editUserResponse

    fun getAllUsers(token: String) {
        performRequest(
            request = { userRepository.getAllUsers(token) },
            responseLiveData = _getAllUsersResponse
        )
    }

    fun getUsersWithRole(token: String, roleName: String) {
        performRequest(
            request = { userRepository.getUsersWithRole(token, roleName) },
            responseLiveData = _getUsersWithRoleResponse
        )
    }

    fun editUser(token: String, userDto: UserDto) {
        performRequest(
            request = { userRepository.editUser(token, userDto) },
            responseLiveData = _editUserResponse
        )
    }
}