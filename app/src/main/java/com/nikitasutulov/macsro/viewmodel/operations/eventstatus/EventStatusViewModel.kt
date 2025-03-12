package com.nikitasutulov.macsro.viewmodel.operations.eventstatus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.CreateEventStatusDto
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
import com.nikitasutulov.macsro.repository.operations.EventStatusRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class EventStatusViewModel(private val eventStatusRepository: EventStatusRepository): ViewModel() {
    private val _getAllEventStatusesResponse = MutableLiveData<BaseResponse<List<EventStatusDto>>>()
    val getAllEventStatusesResponse: LiveData<BaseResponse<List<EventStatusDto>>> = _getAllEventStatusesResponse

    private val _createEventStatusResponse = MutableLiveData<BaseResponse<EventStatusDto>>()
    val createEventStatusResponse: LiveData<BaseResponse<EventStatusDto>> = _createEventStatusResponse

    private val _editEventStatusResponse = MutableLiveData<BaseResponse<EventStatusDto>>()
    val editEventStatusResponse: LiveData<BaseResponse<EventStatusDto>> = _editEventStatusResponse

    private val _getEventStatusByGIDResponse = MutableLiveData<BaseResponse<EventStatusDto>>()
    val getEventStatusByGIDResponse: LiveData<BaseResponse<EventStatusDto>> = _getEventStatusByGIDResponse

    private val _deleteEventStatusByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteEventStatusByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteEventStatusByGIDResponse

    fun getAllEventStatuses(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { eventStatusRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllEventStatusesResponse
        )
    }

    fun createEventStatus(name: String) {
        performRequest(
            request = { eventStatusRepository.create(CreateEventStatusDto(name)) },
            responseLiveData = _createEventStatusResponse
        )
    }

    fun editEventStatus(gid: String, name: String) {
        performRequest(
            request = { eventStatusRepository.edit(EventStatusDto(gid, name)) },
            responseLiveData = _editEventStatusResponse
        )
    }

    fun getEventStatusByGID(gid: String) {
        performRequest(
            request = { eventStatusRepository.getByGID(gid) },
            responseLiveData = _getEventStatusByGIDResponse
        )
    }

    fun deleteEventStatusByGID(gid: String) {
        performRequest(
            request = { eventStatusRepository.deleteByGID(gid) },
            responseLiveData = _deleteEventStatusByGIDResponse
        )
    }
}