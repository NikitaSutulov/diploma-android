package com.nikitasutulov.macsro.repository.operations

import com.nikitasutulov.macsro.data.dto.operations.eventstatus.CreateEventStatusDto
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class EventStatusRepository {
    private val eventStatusApi = RetrofitClient.eventStatusApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) = eventStatusApi.getAllEventStatuses(pageNumber, pageSize)

    suspend fun create(createEventStatusDto: CreateEventStatusDto) = eventStatusApi.createEventStatus(createEventStatusDto)

    suspend fun edit(eventStatusDto: EventStatusDto) = eventStatusApi.editEventStatus(eventStatusDto)

    suspend fun getByGID(gid: String) = eventStatusApi.getEventStatusByGID(gid)

    suspend fun deleteByGID(gid: String) = eventStatusApi.deleteEventStatusByGID(gid)
}