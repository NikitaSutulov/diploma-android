package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventPaginationQueryDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventsListResultDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class EventViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.eventApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<EventDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<EventDto>>> = _getAllResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<EventDto>>()
    val getByGIDResponse: LiveData<BaseResponse<EventDto>> = _getByGIDResponse

    fun getAll(token: String, pageNumber: Int? = null, pageSize: Int? = null) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun getSorted(token: String, eventStatusGID: String): LiveData<BaseResponse<EventsListResultDto>> {
        val responseMutableLiveData = MutableLiveData<BaseResponse<EventsListResultDto>>()
        val responseLiveData: LiveData<BaseResponse<EventsListResultDto>> = responseMutableLiveData
        performRequest(responseMutableLiveData) { api.getSorted(token, EventPaginationQueryDto(eventStatusGID)) }
        return responseLiveData
    }
}
