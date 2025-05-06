package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.request.CreateRequestDto
import com.nikitasutulov.macsro.data.dto.operations.request.RequestDto
import com.nikitasutulov.macsro.data.dto.operations.request.RequestPaginationQueryDto
import com.nikitasutulov.macsro.data.dto.operations.request.RequestsListResultDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class RequestViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.requestApi

    fun getAll(
        token: String,
        requestPaginationQueryDto: RequestPaginationQueryDto
    ): LiveData<BaseResponse<RequestsListResultDto>> {
        val responseMutableLiveData = MutableLiveData<BaseResponse<RequestsListResultDto>>()
        val responseLiveData: LiveData<BaseResponse<RequestsListResultDto>> = responseMutableLiveData
        performRequest(responseMutableLiveData) { api.getAll(token, requestPaginationQueryDto) }
        return responseLiveData
    }

    fun create(
        token: String,
        createRequestDto: CreateRequestDto
    ): LiveData<BaseResponse<RequestDto>> {
        val responseMutableLiveData = MutableLiveData<BaseResponse<RequestDto>>()
        val responseLiveData: LiveData<BaseResponse<RequestDto>> = responseMutableLiveData
        performRequest(responseMutableLiveData) { api.create(token, createRequestDto) }
        return responseLiveData
    }

    fun read(
        token: String,
        requestGID: String
    ): LiveData<BaseResponse<ResponseBody>> {
        val responseMutableLiveData = MutableLiveData<BaseResponse<ResponseBody>>()
        val responseLiveData: LiveData<BaseResponse<ResponseBody>> = responseMutableLiveData
        performRequest(responseMutableLiveData) { api.read(token, requestGID) }
        return responseLiveData
    }
}
