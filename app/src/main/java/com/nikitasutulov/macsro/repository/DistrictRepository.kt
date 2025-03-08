package com.nikitasutulov.macsro.repository

import com.nikitasutulov.macsro.data.dto.district.CreateDistrictDto
import com.nikitasutulov.macsro.data.dto.district.DistrictDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class DistrictRepository {
    private val districtApi = RetrofitClient.districtApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) = districtApi.getAllDistricts(pageNumber, pageSize)

    suspend fun create(createDistrictDto: CreateDistrictDto) = districtApi.createDistrict(createDistrictDto)

    suspend fun edit(districtDto: DistrictDto) = districtApi.editDistrict(districtDto)

    suspend fun getByGID(gid: String) = districtApi.getDistrictByGID(gid)

    suspend fun deleteByGID(gid: String) = districtApi.deleteDistrictByGID(gid)
}