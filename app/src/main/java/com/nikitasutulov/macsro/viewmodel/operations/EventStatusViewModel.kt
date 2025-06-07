package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class EventStatusViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.eventStatusApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<EventStatusDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<EventStatusDto>>> = _getAllResponse

    fun getAll(token: String, pageNumber: Int? = null, pageSize: Int? = null) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }
}
