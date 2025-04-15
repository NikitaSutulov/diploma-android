package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.CreateResourcesEventDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.repository.ResourcesEventRepository
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class ResourcesEventViewModel(private val repository: ResourcesEventRepository) : ApiClientViewModel() {
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
        performRequest(
            request = { repository.getAll(token, pageNumber, pageSize) },
            responseLiveData = _getAllResponse
        )
    }

    fun create(token: String, createDto: CreateResourcesEventDto) {
        performRequest(
            request = { repository.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun edit(token: String, dto: ResourcesEventDto) {
        performRequest(
            request = { repository.edit(token, dto) },
            responseLiveData = _editResponse
        )
    }

    fun getByEventGID(token: String, eventGID: String) {
        performRequest(
            request = { repository.getByEventGID(token, eventGID) },
            responseLiveData = _getByEventGIDResponse
        )
    }

    fun getByResourceGID(token: String, resourceGID: String) {
        performRequest(
            request = { repository.getByResourceGID(token, resourceGID) },
            responseLiveData = _getByResourceGIDResponse
        )
    }

    fun getByGID(token: String, gid: String) {
        performRequest(
            request = { repository.getByGID(token, gid) },
            responseLiveData = _getByGIDResponse
        )
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(
            request = { repository.deleteByGID(token, gid) },
            responseLiveData = _deleteByGIDResponse
        )
    }
}
