package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.CreateEventStatusDto
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class EventStatusViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.eventStatusApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<EventStatusDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<EventStatusDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<EventStatusDto>>()
    val createResponse: LiveData<BaseResponse<EventStatusDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<EventStatusDto>>()
    val editResponse: LiveData<BaseResponse<EventStatusDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<EventStatusDto>>()
    val getByGIDResponse: LiveData<BaseResponse<EventStatusDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { api.getAll(token, pageNumber, pageSize) },
            responseLiveData = _getAllResponse
        )
    }

    fun create(token: String, createDto: CreateEventStatusDto) {
        performRequest(
            request = { api.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun edit(token: String, dto: EventStatusDto) {
        performRequest(
            request = { api.edit(token, dto) },
            responseLiveData = _editResponse
        )
    }

    fun getByGID(token: String, gid: String) {
        performRequest(
            request = { api.getByGID(token, gid) },
            responseLiveData = _getByGIDResponse
        )
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(
            request = { api.deleteByGID(token, gid) },
            responseLiveData = _deleteByGIDResponse
        )
    }
}