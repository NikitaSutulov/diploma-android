package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.event.CreateEventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class EventViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.eventApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<EventDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<EventDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<EventDto>>()
    val createResponse: LiveData<BaseResponse<EventDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<EventDto>>()
    val editResponse: LiveData<BaseResponse<EventDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<EventDto>>()
    val getByGIDResponse: LiveData<BaseResponse<EventDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { api.getAll(token, pageNumber, pageSize) },
            responseLiveData = _getAllResponse
        )
    }

    fun create(token: String, createDto: CreateEventDto) {
        performRequest(
            request = { api.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun edit(token: String, dto: EventDto) {
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