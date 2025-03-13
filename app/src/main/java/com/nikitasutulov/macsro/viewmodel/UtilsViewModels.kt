package com.nikitasutulov.macsro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.district.CreateDistrictDto
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.CreateMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.MeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.resource.CreateResourceDto
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.CreateResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.ResourceMeasurementUnitDto
import com.nikitasutulov.macsro.repository.DistrictRepository
import com.nikitasutulov.macsro.repository.MeasurementUnitRepository
import com.nikitasutulov.macsro.repository.ResourceMeasurementUnitRepository
import com.nikitasutulov.macsro.repository.ResourceRepository
import okhttp3.ResponseBody

class DistrictViewModel(repository: DistrictRepository): CrudViewModel<DistrictDto, CreateDistrictDto>(repository)

class MeasurementUnitViewModel(repository: MeasurementUnitRepository): CrudViewModel<MeasurementUnitDto, CreateMeasurementUnitDto>(repository)

class ResourceViewModel(repository: ResourceRepository): CrudViewModel<ResourceDto, CreateResourceDto>(repository)

class ResourceMeasurementUnitViewModel(private val repository: ResourceMeasurementUnitRepository): ApiClientViewModel() {
    private val _getByUnitGIDResponse = MutableLiveData<BaseResponse<List<ResourceMeasurementUnitDto>>>()
    val getByUnitGIDResponse: LiveData<BaseResponse<List<ResourceMeasurementUnitDto>>> = _getByUnitGIDResponse

    private val _getByResourceGIDResponse = MutableLiveData<BaseResponse<List<ResourceMeasurementUnitDto>>>()
    val getByResourceGIDResponse: LiveData<BaseResponse<List<ResourceMeasurementUnitDto>>> = _getByResourceGIDResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<ResourceMeasurementUnitDto>>()
    val getByGIDResponse: LiveData<BaseResponse<ResourceMeasurementUnitDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    private val _createResponse = MutableLiveData<BaseResponse<ResourceMeasurementUnitDto>>()
    val createResponse: LiveData<BaseResponse<ResourceMeasurementUnitDto>> = _createResponse

    private val _existsResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val existsResponse: LiveData<BaseResponse<ResponseBody>> = _existsResponse

    fun getByUnitGID(unitGID: String) {
        performRequest(
            request = { repository.getByUnitGID(unitGID) },
            responseLiveData = _getByUnitGIDResponse
        )
    }

    fun getByResourceGID(resourceGID: String) {
        performRequest(
            request = { repository.getByResourceGID(resourceGID) },
            responseLiveData = _getByResourceGIDResponse
        )
    }

    fun getByGID(gid: String) {
        performRequest(
            request = { repository.getByGID(gid) },
            responseLiveData = _getByGIDResponse
        )
    }

    fun deleteByGID(gid: String) {
        performRequest(
            request = { repository.deleteByGID(gid) },
            responseLiveData = _deleteByGIDResponse
        )
    }

    fun create(createDto: CreateResourceMeasurementUnitDto) {
        performRequest(
            request = { repository.create(createDto) },
            responseLiveData = _createResponse
        )
    }

    fun exists(dto: ResourceMeasurementUnitDto) {
        performRequest(
            request = { repository.exists(dto) },
            responseLiveData = _existsResponse
        )
    }
}