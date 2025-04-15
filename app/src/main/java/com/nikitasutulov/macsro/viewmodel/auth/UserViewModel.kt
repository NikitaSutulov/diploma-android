package com.nikitasutulov.macsro.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.repository.UserRepository
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

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
