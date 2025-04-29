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
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun create(token: String, createDto: CreateEventStatusDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun edit(token: String, dto: EventStatusDto) {
        performRequest(_editResponse) { api.edit(token, dto) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }
}
