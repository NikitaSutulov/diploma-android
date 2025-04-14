package com.nikitasutulov.macsro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginDto
import com.nikitasutulov.macsro.data.dto.auth.auth.RegisterDto
import com.nikitasutulov.macsro.data.dto.auth.auth.SuccessfulLoginResponseDto
import com.nikitasutulov.macsro.data.dto.auth.role.RoleDto
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.repository.AuthRepository
import com.nikitasutulov.macsro.repository.RoleRepository
import com.nikitasutulov.macsro.repository.UserRepository
import okhttp3.ResponseBody

class AuthViewModel(private val repository: AuthRepository) : ApiClientViewModel() {
    private val _registerResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val registerResponse: LiveData<BaseResponse<ResponseBody>> = _registerResponse

    private val _loginResponse = MutableLiveData<BaseResponse<SuccessfulLoginResponseDto>>()
    val loginResponse: LiveData<BaseResponse<SuccessfulLoginResponseDto>> = _loginResponse

    fun register(registerDto: RegisterDto) {
        performRequest(
            request = { repository.registerVolunteer(registerDto) },
            responseLiveData = _registerResponse
        )
    }

    fun login(loginDto: LoginDto) {
        performRequest(
            request = { repository.login(loginDto) },
            responseLiveData = _loginResponse
        )
    }
}

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

class UserViewModel(private val repository: UserRepository) : ApiClientViewModel() {
    private val _getAllResponse = MutableLiveData<BaseResponse<List<UserDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<UserDto>>> = _getAllResponse

    private val _getWithUsernameResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val getWithUsernameResponse: LiveData<BaseResponse<ResponseBody>> = _getWithUsernameResponse

    private val _getWithEmailResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val getWithEmailResponse: LiveData<BaseResponse<ResponseBody>> = _getWithEmailResponse

    private val _getWithRoleResponse = MutableLiveData<BaseResponse<List<UserDto>>>()
    val getWithRoleResponse: LiveData<BaseResponse<List<UserDto>>> = _getWithRoleResponse

    private val _editResponse = MutableLiveData<BaseResponse<UserDto>>()
    val editResponse: LiveData<BaseResponse<UserDto>> = _editResponse

    fun getAll(token: String) {
        performRequest(
            request = { repository.getAllUsers(token) },
            responseLiveData = _getAllResponse
        )
    }

    fun getWithUsername(token: String, username: String) {
        performRequest(
            request = { repository.getUsersWithUsername(token, username) },
            responseLiveData = _getWithUsernameResponse
        )
    }

    fun getWithEmail(token: String, email: String) {
        performRequest(
            request = { repository.getUsersWithEmail(token, email) },
            responseLiveData = _getWithEmailResponse
        )
    }

    fun getWithRole(token: String, roleName: String) {
        performRequest(
            request = { repository.getUsersWithRole(token, roleName) },
            responseLiveData = _getWithRoleResponse
        )
    }

    fun edit(token: String, userDto: UserDto) {
        performRequest(
            request = { repository.editUser(token, userDto) },
            responseLiveData = _editResponse
        )
    }
}
