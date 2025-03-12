package com.nikitasutulov.macsro.repository.utils

import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.CreateResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.ResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class ResourceMeasurementUnitRepository {
    private val resourceMeasurementUnitApi = RetrofitClient.resourceMeasurementUnitApi

    suspend fun getByUnitGID(unitGID: String) =
        resourceMeasurementUnitApi.getResourceMeasurementUnitsByUnitGID(unitGID)

    suspend fun getByResourceGID(resourceGID: String) =
        resourceMeasurementUnitApi.getResourceMeasurementUnitsByResourceGID(resourceGID)

    suspend fun getByGID(gid: String) =
        resourceMeasurementUnitApi.getResourceMeasurementUnitByGID(gid)

    suspend fun deleteByGID(gid: String) =
        resourceMeasurementUnitApi.deleteResourceMeasurementUnitByGID(gid)

    suspend fun create(createResourceMeasurementUnitDto: CreateResourceMeasurementUnitDto) =
        resourceMeasurementUnitApi.createResourceMeasurementUnit(createResourceMeasurementUnitDto)

    suspend fun exists(resourceMeasurementUnitDto: ResourceMeasurementUnitDto) =
        resourceMeasurementUnitApi.existsResourceMeasurementUnit(resourceMeasurementUnitDto)
}