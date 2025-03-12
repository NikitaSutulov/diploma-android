package com.nikitasutulov.macsro.repository.operations

import com.nikitasutulov.macsro.data.dto.operations.event.CreateEventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class EventRepository {
    private val eventApi = RetrofitClient.eventApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) = eventApi.getAllEvents(pageNumber, pageSize)

    suspend fun create(createEventDto: CreateEventDto) = eventApi.createEvent(createEventDto)

    suspend fun edit(eventDto: EventDto) = eventApi.editEvent(eventDto)

    suspend fun getByGID(gid: String) = eventApi.getEventByGID(gid)

    suspend fun deleteByGID(gid: String) = eventApi.deleteEventByGID(gid)
}