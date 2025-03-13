package com.nikitasutulov.macsro.viewmodel.utils.resourcemeasurementunit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.CreateResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.ResourceMeasurementUnitDto
import com.nikitasutulov.macsro.repository.ResourceMeasurementUnitRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class ResourceMeasurementUnitViewModel(private val resourceMeasurementUnitRepository: ResourceMeasurementUnitRepository): ViewModel() {
    private val _getResourceMeasurementUnitsByUnitGIDResponse = MutableLiveData<BaseResponse<List<ResourceMeasurementUnitDto>>>()
    val getResourceMeasurementUnitsByUnitGIDResponse: LiveData<BaseResponse<List<ResourceMeasurementUnitDto>>> = _getResourceMeasurementUnitsByUnitGIDResponse

    private val _getResourceMeasurementUnitsByResourceGIDResponse = MutableLiveData<BaseResponse<List<ResourceMeasurementUnitDto>>>()
    val getResourceMeasurementUnitsByResourceGIDResponse: LiveData<BaseResponse<List<ResourceMeasurementUnitDto>>> = _getResourceMeasurementUnitsByResourceGIDResponse

    private val _getResourceMeasurementUnitByGIDResponse = MutableLiveData<BaseResponse<ResourceMeasurementUnitDto>>()
    val getResourceMeasurementUnitByGIDResponse: LiveData<BaseResponse<ResourceMeasurementUnitDto>> = _getResourceMeasurementUnitByGIDResponse

    private val _deleteResourceMeasurementUnitByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteResourceMeasurementUnitByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteResourceMeasurementUnitByGIDResponse

    private val _createResourceMeasurementUnitResponse = MutableLiveData<BaseResponse<ResourceMeasurementUnitDto>>()
    val createResourceMeasurementUnitResponse: LiveData<BaseResponse<ResourceMeasurementUnitDto>> = _createResourceMeasurementUnitResponse

    private val _existsResourceMeasurementUnitResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val existsResourceMeasurementUnitResponse: LiveData<BaseResponse<ResponseBody>> = _existsResourceMeasurementUnitResponse

    fun getResourceMeasurementUnitsByUnitGID(unitGID: String) {
        performRequest(
            request = { resourceMeasurementUnitRepository.getByUnitGID(unitGID) },
            responseLiveData = _getResourceMeasurementUnitsByUnitGIDResponse
        )
    }

    fun getResourceMeasurementUnitsByResourceGID(resourceGID: String) {
        performRequest(
            request = { resourceMeasurementUnitRepository.getByResourceGID(resourceGID) },
            responseLiveData = _getResourceMeasurementUnitsByResourceGIDResponse
        )
    }

    fun getResourceMeasurementUnitByGID(gid: String) {
        performRequest(
            request = { resourceMeasurementUnitRepository.getByGID(gid) },
            responseLiveData = _getResourceMeasurementUnitByGIDResponse
        )
    }

    fun deleteResourceMeasurementUnitByGID(gid: String) {
        performRequest(
            request = { resourceMeasurementUnitRepository.deleteByGID(gid) },
            responseLiveData = _deleteResourceMeasurementUnitByGIDResponse
        )
    }

    fun createResourceMeasurementUnit(unitGID: String, resourceGID: String) {
        performRequest(
            request = { resourceMeasurementUnitRepository.create(CreateResourceMeasurementUnitDto(unitGID, resourceGID)) },
            responseLiveData = _createResourceMeasurementUnitResponse
        )
    }

    fun existsResourceMeasurementUnit(resourceMeasurementUnitDto: ResourceMeasurementUnitDto) {
        performRequest(
            request = { resourceMeasurementUnitRepository.exists(resourceMeasurementUnitDto) },
            responseLiveData = _existsResourceMeasurementUnitResponse
        )
    }
}