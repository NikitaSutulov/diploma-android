package com.nikitasutulov.macsro.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.ErrorResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

abstract class ApiClientViewModel(): ViewModel() {
    fun <T> performRequest(
        request: suspend () -> retrofit2.Response<T>,
        responseLiveData: MutableLiveData<BaseResponse<T>>
    ) {
        responseLiveData.postValue(BaseResponse.Loading())

        viewModelScope.launch(Dispatchers.IO + createExceptionHandler()) {
            val response = request()
            val result = if (response.isSuccessful) {
                BaseResponse.Success(response.body())
            } else {
                val errorMessage = JSONObject(response.errorBody()!!.string()).get("message") as String
                BaseResponse.Error(ErrorResponse(errorMessage))
            }

            responseLiveData.postValue(result)
        }
    }

    private fun createExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            Log.e(this::class.java.simpleName, "Exception handled: ${throwable.stackTraceToString()}")
        }
    }
}
