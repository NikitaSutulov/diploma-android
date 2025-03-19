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

class ResourceMeasurementUnitRepository(private val api: ResourceMeasurementUnitApi): Repository() {
    suspend fun getByUnitGID(token: String, unitGID: String) =
        api.getByUnitGID(token, unitGID)

    suspend fun getByResourceGID(token: String, resourceGID: String) =
        api.getByResourceGID(token, resourceGID)

    suspend fun getByGID(token: String, gid: String) =
        api.getByGID(token, gid)

    suspend fun deleteByGID(token: String, gid: String) =
        api.deleteByGID(token, gid)

    suspend fun create(token: String, createResourceMeasurementUnitDto: CreateResourceMeasurementUnitDto) =
        api.create(token, createResourceMeasurementUnitDto)

    suspend fun exists(token: String, resourceMeasurementUnitDto: ResourceMeasurementUnitDto) =
        api.exists(token, resourceMeasurementUnitDto)
}
