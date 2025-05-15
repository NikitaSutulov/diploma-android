package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class EventTypeViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.eventTypeApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<EventTypeDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<EventTypeDto>>> = _getAllResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<EventTypeDto>>()
    val getByGIDResponse: LiveData<BaseResponse<EventTypeDto>> = _getByGIDResponse

    fun getAll(token: String, pageNumber: Int? = null, pageSize: Int? = null) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }
}
