package com.nikitasutulov.macsro.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginDto
import com.nikitasutulov.macsro.data.dto.auth.auth.RegisterDto
import com.nikitasutulov.macsro.data.dto.auth.auth.SuccessfulLoginResponseDto
import com.nikitasutulov.macsro.data.dto.auth.auth.TokenValidationResponseDto
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class AuthViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.authApi

    private val _registerResponse = MutableLiveData<BaseResponse<UserDto>>()
    val registerResponse: LiveData<BaseResponse<UserDto>> = _registerResponse

    private val _loginResponse = MutableLiveData<BaseResponse<SuccessfulLoginResponseDto>>()
    val loginResponse: LiveData<BaseResponse<SuccessfulLoginResponseDto>> = _loginResponse

    private val _tokenValidationResponse =
        MutableLiveData<BaseResponse<TokenValidationResponseDto>>()
    val tokenValidationResponse: LiveData<BaseResponse<TokenValidationResponseDto>> =
        _tokenValidationResponse

    fun register(registerDto: RegisterDto) {
        performRequest(
            request = { api.register(registerDto) },
            responseLiveData = _registerResponse
        )
    }

    fun login(loginDto: LoginDto) {
        performRequest(
            request = { api.login(loginDto) },
            responseLiveData = _loginResponse
        )
    }

    fun clearLoginResponse() {
        _loginResponse.value = BaseResponse.Loading()
    }

    fun validateToken(token: String) {
        performRequest(
            request = { api.validateToken(token) },
            responseLiveData = _tokenValidationResponse
        )
    }
}