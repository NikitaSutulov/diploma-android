package com.nikitasutulov.macsro.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

abstract class ApiClientViewModel(): ViewModel() {
    fun <T> performRequest(
        request: suspend () -> retrofit2.Response<T>,
        responseLiveData: MutableLiveData<BaseResponse<T>>
    ) {
        responseLiveData.postValue(BaseResponse.Loading())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request()
                val result = if (response.isSuccessful) {
                    BaseResponse.Success(response.body())
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        JSONObject(errorBody ?: "").getString("message")
                    } catch (e: Exception) {
                        "Unknown error occurred"
                    }
                    BaseResponse.Error(ErrorResponse(errorMessage))
                }
                responseLiveData.postValue(result)
            } catch (e: Exception) {
                Log.e(this::class.java.simpleName, "Exception handled: ${e.stackTraceToString()}")
                val message = when (e) {
                    is java.net.ConnectException -> "Failed to connect to the server"
                    is retrofit2.HttpException -> "Server error: ${e.message}"
                    else -> "Unexpected error: ${e.localizedMessage}"
                }
                responseLiveData.postValue(BaseResponse.Error(ErrorResponse(message)))
            }
        }
    }

}
