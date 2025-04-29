package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.eventtype.CreateEventTypeDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class EventTypeViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.eventTypeApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<EventTypeDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<EventTypeDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<EventTypeDto>>()
    val createResponse: LiveData<BaseResponse<EventTypeDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<EventTypeDto>>()
    val editResponse: LiveData<BaseResponse<EventTypeDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<EventTypeDto>>()
    val getByGIDResponse: LiveData<BaseResponse<EventTypeDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun create(token: String, createDto: CreateEventTypeDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun edit(token: String, dto: EventTypeDto) {
        performRequest(_editResponse) { api.edit(token, dto) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }
}
