package com.nikitasutulov.macsro.repository.utils

import com.nikitasutulov.macsro.data.dto.utils.measurementunit.CreateMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.MeasurementUnitDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class MeasurementUnitRepository {
    private val measurementUnitApi = RetrofitClient.measurementUnitApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) =
        measurementUnitApi.getAllMeasurementUnits(pageNumber, pageSize)

    suspend fun create(createMeasurementUnitDto: CreateMeasurementUnitDto) =
        measurementUnitApi.createMeasurementUnit(createMeasurementUnitDto)

    suspend fun edit(measurementUnitDto: MeasurementUnitDto) =
        measurementUnitApi.editMeasurementUnit(measurementUnitDto)

    suspend fun getByGID(gid: String) = measurementUnitApi.getMeasurementUnitByGID(gid)

    suspend fun deleteByGID(gid: String) = measurementUnitApi.deleteMeasurementUnitByGID(gid)
}