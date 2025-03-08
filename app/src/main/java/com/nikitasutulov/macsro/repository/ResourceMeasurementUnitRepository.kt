package com.nikitasutulov.macsro.repository

import com.nikitasutulov.macsro.data.dto.resourcemeasurementunit.CreateResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.resourcemeasurementunit.ResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class ResourceMeasurementUnitRepository {
    private val resourceMeasurementUnitApi = RetrofitClient.resourceMeasurementUnitApi

    suspend fun getByUnitGID(unitGID: String) =
        resourceMeasurementUnitApi.getResourceMeasurementUnitsByUnitGID(unitGID)

    suspend fun getByResourceGID(resourceGID: String) =
        resourceMeasurementUnitApi.getResourceMeasurementUnitsByUnitGID(resourceGID)

    suspend fun getByGID(gid: String) =
        resourceMeasurementUnitApi.getResourceMeasurementUnitByGID(gid)

    suspend fun deleteByGID(gid: String) =
        resourceMeasurementUnitApi.deleteResourceMeasurementUnitByGID(gid)

    suspend fun create(createResourceMeasurementUnitDto: CreateResourceMeasurementUnitDto) =
        resourceMeasurementUnitApi.createResourceMeasurementUnit(createResourceMeasurementUnitDto)

    suspend fun exists(resourceMeasurementUnitDto: ResourceMeasurementUnitDto) =
        resourceMeasurementUnitApi.existsResourceMeasurementUnit(resourceMeasurementUnitDto)
}