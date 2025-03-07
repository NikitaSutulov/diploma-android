package com.nikitasutulov.macsro.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun <T> ViewModel.performRequest(
    request: suspend () -> retrofit2.Response<T>,
    responseLiveData: MutableLiveData<BaseResponse<T>>
) {
    responseLiveData.postValue(BaseResponse.Loading())

    viewModelScope.launch(Dispatchers.IO + createExceptionHandler(this)) {
        val response = request()
        val result = if (response.isSuccessful) {
            BaseResponse.Success(response.body())
        } else {
            BaseResponse.Error(ErrorResponse(response.errorBody()!!.string()))
        }

        responseLiveData.postValue(result)
    }
}