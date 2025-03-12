package com.nikitasutulov.macsro.repository.operations

import com.nikitasutulov.macsro.data.dto.operations.eventtype.CreateEventTypeDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class EventTypeRepository {
    private val eventTypeApi = RetrofitClient.eventTypeApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) = eventTypeApi.getAllEventTypes(pageNumber, pageSize)

    suspend fun create(createEventTypeDto: CreateEventTypeDto) = eventTypeApi.createEventType(createEventTypeDto)

    suspend fun edit(eventTypeDto: EventTypeDto) = eventTypeApi.editEventType(eventTypeDto)

    suspend fun getByGID(gid: String) = eventTypeApi.getEventTypeByGID(gid)

    suspend fun deleteByGID(gid: String) = eventTypeApi.deleteEventTypeByGID(gid)
}