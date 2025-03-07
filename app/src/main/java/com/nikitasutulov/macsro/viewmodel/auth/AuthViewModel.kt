package com.nikitasutulov.macsro.viewmodel.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.auth.LoginDto
import com.nikitasutulov.macsro.data.dto.auth.RegisterDto
import com.nikitasutulov.macsro.data.dto.auth.SuccessfulLoginResponseDto
import com.nikitasutulov.macsro.repository.AuthRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class AuthViewModel(private val authRepository: AuthRepository): ViewModel() {
    private val _registerResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val registerResponse: LiveData<BaseResponse<ResponseBody>> = _registerResponse

    private val _loginResponse = MutableLiveData<BaseResponse<SuccessfulLoginResponseDto>>()
    val loginResponse: LiveData<BaseResponse<SuccessfulLoginResponseDto>> = _loginResponse

    fun registerVolunteer(username: String, email: String, password: String) {
        performRequest(
            request = { authRepository.registerVolunteer(RegisterDto(username, email, password)) },
            responseLiveData = _registerResponse
        )
    }

    fun login(username: String, password: String) {
        performRequest(
            request = { authRepository.login(LoginDto(username, password)) },
            responseLiveData = _loginResponse
        )
    }

//    private fun parseError(errorBody: ResponseBody?): ErrorResponse? {
////        return errorBody?.charStream()?.let {
////            Gson().fromJson(it, object : TypeToken<ErrorResponse>() {}.type)
////        }
//        return errorBody?.charStream()?.use { reader ->
//            val responseText = reader.readText()
//            Log.e("Auth Error", "Error response: $responseText")
//
//            try {
//                return Gson().fromJson(responseText, object : TypeToken<ErrorResponse>() {}.type)
//            } catch (e: JsonSyntaxException) {
//                Log.e("Parsing error","JSON parsing error: ${e.message}")
//                null
//            }
//        }
//    }

}