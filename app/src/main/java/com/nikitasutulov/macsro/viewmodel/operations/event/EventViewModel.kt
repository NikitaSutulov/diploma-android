package com.nikitasutulov.macsro.viewmodel.operations.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.event.CreateEventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
import com.nikitasutulov.macsro.repository.operations.EventRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class EventViewModel(private val eventRepository: EventRepository): ViewModel() {
    private val _getAllEventsResponse = MutableLiveData<BaseResponse<List<EventDto>>>()
    val getAllEventsResponse: LiveData<BaseResponse<List<EventDto>>> = _getAllEventsResponse

    private val _createEventResponse = MutableLiveData<BaseResponse<EventDto>>()
    val createEventResponse: LiveData<BaseResponse<EventDto>> = _createEventResponse

    private val _editEventResponse = MutableLiveData<BaseResponse<EventDto>>()
    val editEventResponse: LiveData<BaseResponse<EventDto>> = _editEventResponse

    private val _getEventByGIDResponse = MutableLiveData<BaseResponse<EventDto>>()
    val getEventByGIDResponse: LiveData<BaseResponse<EventDto>> = _getEventByGIDResponse

    private val _deleteEventByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteEventByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteEventByGIDResponse

    fun getAllEvents(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { eventRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllEventsResponse
        )
    }

    fun createEvent(createEventDto: CreateEventDto) {
        performRequest(
            request = { eventRepository.create(createEventDto) },
            responseLiveData = _createEventResponse
        )
    }

    fun editEvent(eventDto: EventDto) {
        performRequest(
            request = { eventRepository.edit(eventDto) },
            responseLiveData = _editEventResponse
        )
    }

    fun getEventByGID(gid: String) {
        performRequest(
            request = { eventRepository.getByGID(gid) },
            responseLiveData = _getEventByGIDResponse
        )
    }

    fun deleteEventByGID(gid: String) {
        performRequest(
            request = { eventRepository.deleteByGID(gid) },
            responseLiveData = _deleteEventByGIDResponse
        )
    }
}