package com.nikitasutulov.macsro.viewmodel.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.CreateResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.resourcemeasurementunit.ResourceMeasurementUnitDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class ResourceMeasurementUnitViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.resourceMeasurementUnitApi

    private val _getByUnitGIDResponse =
        MutableLiveData<BaseResponse<List<ResourceMeasurementUnitDto>>>()
    val getByUnitGIDResponse: LiveData<BaseResponse<List<ResourceMeasurementUnitDto>>> =
        _getByUnitGIDResponse

    private val _getByResourceGIDResponse =
        MutableLiveData<BaseResponse<List<ResourceMeasurementUnitDto>>>()
    val getByResourceGIDResponse: LiveData<BaseResponse<List<ResourceMeasurementUnitDto>>> =
        _getByResourceGIDResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<ResourceMeasurementUnitDto>>()
    val getByGIDResponse: LiveData<BaseResponse<ResourceMeasurementUnitDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    private val _createResponse = MutableLiveData<BaseResponse<ResourceMeasurementUnitDto>>()
    val createResponse: LiveData<BaseResponse<ResourceMeasurementUnitDto>> = _createResponse

    private val _existsResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val existsResponse: LiveData<BaseResponse<ResponseBody>> = _existsResponse

    fun getByUnitGID(token: String, unitGID: String) {
        performRequest(_getByUnitGIDResponse) { api.getByUnitGID(token, unitGID) }
    }

    fun getByResourceGID(token: String, resourceGID: String) {
        performRequest(_getByResourceGIDResponse) { api.getByResourceGID(token, resourceGID) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }

    fun create(token: String, createDto: CreateResourceMeasurementUnitDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun exists(token: String, dto: ResourceMeasurementUnitDto) {
        performRequest(_existsResponse) { api.exists(token, dto) }
    }
}
