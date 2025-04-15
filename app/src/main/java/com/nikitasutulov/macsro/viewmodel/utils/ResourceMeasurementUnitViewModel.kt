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
        performRequest(
            request = { api.getByUnitGID(token, unitGID) },
            responseLiveData = _getByUnitGIDResponse
        )
    }

    fun getByResourceGID(token: String, resourceGID: String) {
        performRequest(
            request = { api.getByResourceGID(token, resourceGID) },
            responseLiveData = _getByResourceGIDResponse
        )
    }

    fun getByGID(token: String, gid: String) {
        performRequest(
            request = { api.getByGID(token, gid) },
            responseLiveData = _getByGIDResponse
        )
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(
            request = { api.deleteByGID(token, gid) },
            responseLiveData = _deleteByGIDResponse
        )
    }

    fun create(token: String, createDto: CreateResourceMeasurementUnitDto) {
        performRequest(
            request = { api.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun exists(token: String, dto: ResourceMeasurementUnitDto) {
        performRequest(
            request = { api.exists(token, dto) },
            responseLiveData = _existsResponse
        )
    }
}
