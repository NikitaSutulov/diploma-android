package com.nikitasutulov.macsro.repository.operations

import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.CreateResourcesEventDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class ResourcesEventRepository {
    private val resourcesEventApi = RetrofitClient.resourcesEventApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) = resourcesEventApi.getAllResourcesEvents(pageNumber, pageSize)

    suspend fun create(createResourcesEventDto: CreateResourcesEventDto) = resourcesEventApi.createResourcesEvent(createResourcesEventDto)

    suspend fun edit(resourcesEventDto: ResourcesEventDto) = resourcesEventApi.editResourcesEvent(resourcesEventDto)

    suspend fun getByGID(gid: String) = resourcesEventApi.getResourcesEventByGID(gid)

    suspend fun deleteByGID(gid: String) = resourcesEventApi.deleteResourcesEventByGID(gid)
}