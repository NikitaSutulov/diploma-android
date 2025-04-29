package com.nikitasutulov.macsro.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.auth.GetTokenDto
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginDto
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginResponseDto
import com.nikitasutulov.macsro.data.dto.auth.auth.RegisterDto
import com.nikitasutulov.macsro.data.dto.auth.auth.TokenInfoDto
import com.nikitasutulov.macsro.data.dto.auth.auth.TokenValidationResponseDto
import com.nikitasutulov.macsro.data.dto.auth.user.UserDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class AuthViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.authApi

    private val _registerResponse = MutableLiveData<BaseResponse<UserDto>>()
    val registerResponse: LiveData<BaseResponse<UserDto>> = _registerResponse

    private val _loginResponse = MutableLiveData<BaseResponse<LoginResponseDto>>()
    val loginResponse: LiveData<BaseResponse<LoginResponseDto>> = _loginResponse

    private val _getTokenResponse = MutableLiveData<BaseResponse<TokenInfoDto>>()
    val getTokenResponse: LiveData<BaseResponse<TokenInfoDto>> = _getTokenResponse

    private val _tokenValidationResponse =
        MutableLiveData<BaseResponse<TokenValidationResponseDto>>()
    val tokenValidationResponse: LiveData<BaseResponse<TokenValidationResponseDto>> =
        _tokenValidationResponse

    fun register(registerDto: RegisterDto) {
        performRequest(_registerResponse) { api.register(registerDto) }
    }

    fun login(loginDto: LoginDto) {
        performRequest(_loginResponse) { api.login(loginDto) }
    }

    fun getToken(getTokenDto: GetTokenDto) {
        performRequest(_getTokenResponse) { api.getToken(getTokenDto) }
    }

    fun clearLoginResponse() {
        _loginResponse.value = BaseResponse.Loading()
    }

    fun validateToken(token: String) {
        performRequest(_tokenValidationResponse) { api.validateToken(token) }
    }
}
