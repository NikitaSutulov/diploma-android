package com.nikitasutulov.macsro.repository.utils

import com.nikitasutulov.macsro.data.dto.utils.resource.CreateResourceDto
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class ResourceRepository {
    private val resourceApi = RetrofitClient.resourceApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) = resourceApi.getAllResources(pageNumber, pageSize)

    suspend fun create(createResourceDto: CreateResourceDto) = resourceApi.createResource(createResourceDto)

    suspend fun edit(resourceDto: ResourceDto) = resourceApi.editResource(resourceDto)

    suspend fun getByGID(gid: String) = resourceApi.getResourceByGID(gid)

    suspend fun deleteByGID(gid: String) = resourceApi.deleteResourceByGID(gid)
}