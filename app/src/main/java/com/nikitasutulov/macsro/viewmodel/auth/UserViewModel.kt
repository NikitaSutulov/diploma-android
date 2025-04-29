package com.nikitasutulov.macsro.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.ExistsDto
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class UserViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.userApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<UserDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<UserDto>>> = _getAllResponse

    private val _getWithUsernameResponse = MutableLiveData<BaseResponse<ExistsDto>>()
    val getWithUsernameResponse: LiveData<BaseResponse<ExistsDto>> = _getWithUsernameResponse

    private val _getWithEmailResponse = MutableLiveData<BaseResponse<ExistsDto>>()
    val getWithEmailResponse: LiveData<BaseResponse<ExistsDto>> = _getWithEmailResponse

    private val _getWithRoleResponse = MutableLiveData<BaseResponse<List<UserDto>>>()
    val getWithRoleResponse: LiveData<BaseResponse<List<UserDto>>> = _getWithRoleResponse

    private val _editResponse = MutableLiveData<BaseResponse<UserDto>>()
    val editResponse: LiveData<BaseResponse<UserDto>> = _editResponse

    fun getAll(token: String) {
        performRequest(_getAllResponse) { api.getAllUsers(token) }
    }

    fun getWithUsername(username: String) {
        performRequest(_getWithUsernameResponse) { api.getUsersWithUsername(username) }
    }

    fun getWithEmail(email: String) {
        performRequest(_getWithEmailResponse) { api.getUsersWithEmail(email) }
    }

    fun getWithRole(token: String, roleName: String) {
        performRequest(_getWithRoleResponse) { api.getUsersWithRole(token, roleName) }
    }

    fun edit(token: String, userDto: UserDto) {
        performRequest(_editResponse) { api.editUser(token, userDto) }
    }
}
