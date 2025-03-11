package com.nikitasutulov.macsro.viewmodel.utils.resource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.resource.CreateResourceDto
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import com.nikitasutulov.macsro.repository.utils.ResourceRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class ResourceViewModel(private val resourceRepository: ResourceRepository): ViewModel() {
    private val _getAllResourcesResponse = MutableLiveData<BaseResponse<List<ResourceDto>>>()
    val getAllResourcesResponse: LiveData<BaseResponse<List<ResourceDto>>> = _getAllResourcesResponse

    private val _createResourceResponse = MutableLiveData<BaseResponse<ResourceDto>>()
    val createResourceResponse: LiveData<BaseResponse<ResourceDto>> = _createResourceResponse

    private val _editResourceResponse = MutableLiveData<BaseResponse<ResourceDto>>()
    val editResourceResponse: LiveData<BaseResponse<ResourceDto>> = _editResourceResponse

    private val _getResourceByGIDResponse = MutableLiveData<BaseResponse<ResourceDto>>()
    val getResourceByGIDResponse: LiveData<BaseResponse<ResourceDto>> = _getResourceByGIDResponse

    private val _deleteResourceByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteResourceByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteResourceByGIDResponse

    fun getAllResources(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { resourceRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllResourcesResponse
        )
    }

    fun createResource(name: String) {
        performRequest(
            request = { resourceRepository.create(CreateResourceDto(name)) },
            responseLiveData = _createResourceResponse
        )
    }

    fun editResource(gid: String, name: String) {
        performRequest(
            request = { resourceRepository.edit(ResourceDto(gid, name)) },
            responseLiveData = _editResourceResponse
        )
    }

    fun getResourceByGID(gid: String) {
        performRequest(
            request = { resourceRepository.getByGID(gid) },
            responseLiveData = _getResourceByGIDResponse
        )
    }

    fun deleteResourceByGID(gid: String) {
        performRequest(
            request = { resourceRepository.deleteByGID(gid) },
            responseLiveData = _deleteResourceByGIDResponse
        )
    }
}