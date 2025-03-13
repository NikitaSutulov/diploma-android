package com.nikitasutulov.macsro.repository

import com.nikitasutulov.macsro.data.dto.utils.district.CreateDistrictDto
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.CreateMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.MeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.resource.CreateResourceDto
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.CreateResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.ResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.remote.api.utils.DistrictApi
import com.nikitasutulov.macsro.data.remote.api.utils.MeasurementUnitApi
import com.nikitasutulov.macsro.data.remote.api.utils.ResourceApi
import com.nikitasutulov.macsro.data.remote.api.utils.ResourceMeasurementUnitApi

class DistrictRepository(api: DistrictApi): CrudRepository<DistrictDto, CreateDistrictDto>(api)

class MeasurementUnitRepository(api: MeasurementUnitApi): CrudRepository<MeasurementUnitDto, CreateMeasurementUnitDto>(api)

class ResourceRepository(api: ResourceApi): CrudRepository<ResourceDto, CreateResourceDto>(api)

class ResourceMeasurementUnitRepository(private val api: ResourceMeasurementUnitApi) {
    suspend fun getByUnitGID(unitGID: String) =
        api.getByUnitGID(unitGID)

    suspend fun getByResourceGID(resourceGID: String) =
        api.getByResourceGID(resourceGID)

    suspend fun getByGID(gid: String) =
        api.getByGID(gid)

    suspend fun deleteByGID(gid: String) =
        api.deleteByGID(gid)

    suspend fun create(createResourceMeasurementUnitDto: CreateResourceMeasurementUnitDto) =
        api.create(createResourceMeasurementUnitDto)

    suspend fun exists(resourceMeasurementUnitDto: ResourceMeasurementUnitDto) =
        api.exists(resourceMeasurementUnitDto)
}