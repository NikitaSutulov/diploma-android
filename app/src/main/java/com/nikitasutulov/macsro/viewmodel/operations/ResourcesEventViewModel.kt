package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.CreateResourcesEventDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class ResourcesEventViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.resourcesEventApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<ResourcesEventDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<ResourcesEventDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<ResourcesEventDto>>()
    val createResponse: LiveData<BaseResponse<ResourcesEventDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<ResourcesEventDto>>()
    val editResponse: LiveData<BaseResponse<ResourcesEventDto>> = _editResponse

    private val _getByEventGIDResponse = MutableLiveData<BaseResponse<List<ResourcesEventDto>>>()
    val getByEventGIDResponse: LiveData<BaseResponse<List<ResourcesEventDto>>> = _getByEventGIDResponse

    private val _getByResourceGIDResponse = MutableLiveData<BaseResponse<List<ResourcesEventDto>>>()
    val getByResourceGIDResponse: LiveData<BaseResponse<List<ResourcesEventDto>>> = _getByResourceGIDResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<ResourcesEventDto>>()
    val getByGIDResponse: LiveData<BaseResponse<ResourcesEventDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun create(token: String, createDto: CreateResourcesEventDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun edit(token: String, dto: ResourcesEventDto) {
        performRequest(_editResponse) { api.edit(token, dto) }
    }

    fun getByEventGID(token: String, eventGID: String) {
        performRequest(_getByEventGIDResponse) { api.getByEventGID(token, eventGID) }
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
}
