package com.nikitasutulov.macsro.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.auth.LoginDto
import com.nikitasutulov.macsro.data.dto.auth.auth.RegisterDto
import com.nikitasutulov.macsro.data.dto.auth.auth.SuccessfulLoginResponseDto
import com.nikitasutulov.macsro.repository.AuthRepository
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
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