package com.nikitasutulov.macsro.viewmodel.operations.eventtype

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.eventtype.CreateEventTypeDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
import com.nikitasutulov.macsro.repository.EventTypeRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class EventTypeViewModel(private val eventTypeRepository: EventTypeRepository): ViewModel() {
    private val _getAllEventTypesResponse = MutableLiveData<BaseResponse<List<EventTypeDto>>>()
    val getAllEventTypesResponse: LiveData<BaseResponse<List<EventTypeDto>>> = _getAllEventTypesResponse

    private val _createEventTypeResponse = MutableLiveData<BaseResponse<EventTypeDto>>()
    val createEventTypeResponse: LiveData<BaseResponse<EventTypeDto>> = _createEventTypeResponse

    private val _editEventTypeResponse = MutableLiveData<BaseResponse<EventTypeDto>>()
    val editEventTypeResponse: LiveData<BaseResponse<EventTypeDto>> = _editEventTypeResponse

    private val _getEventTypeByGIDResponse = MutableLiveData<BaseResponse<EventTypeDto>>()
    val getEventTypeByGIDResponse: LiveData<BaseResponse<EventTypeDto>> = _getEventTypeByGIDResponse

    private val _deleteEventTypeByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteEventTypeByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteEventTypeByGIDResponse

    fun getAllEventTypes(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { eventTypeRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllEventTypesResponse
        )
    }

    fun createEventType(name: String) {
        performRequest(
            request = { eventTypeRepository.create(CreateEventTypeDto(name)) },
            responseLiveData = _createEventTypeResponse
        )
    }

    fun editEventType(gid: String, name: String) {
        performRequest(
            request = { eventTypeRepository.edit(EventTypeDto(gid, name)) },
            responseLiveData = _editEventTypeResponse
        )
    }

    fun getEventTypeByGID(gid: String) {
        performRequest(
            request = { eventTypeRepository.getByGID(gid) },
            responseLiveData = _getEventTypeByGIDResponse
        )
    }

    fun deleteEventTypeByGID(gid: String) {
        performRequest(
            request = { eventTypeRepository.deleteByGID(gid) },
            responseLiveData = _deleteEventTypeByGIDResponse
        )
    }
}