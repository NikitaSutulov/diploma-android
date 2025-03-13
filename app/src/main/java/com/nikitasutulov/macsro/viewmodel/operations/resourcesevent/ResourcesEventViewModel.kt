package com.nikitasutulov.macsro.viewmodel.operations.resourcesevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.CreateResourcesEventDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.repository.ResourcesEventRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class ResourcesEventViewModel(private val resourcesEventRepository: ResourcesEventRepository): ViewModel() {
    private val _getAllResourcesEventsResponse = MutableLiveData<BaseResponse<List<ResourcesEventDto>>>()
    val getAllResourcesEventsResponse: LiveData<BaseResponse<List<ResourcesEventDto>>> = _getAllResourcesEventsResponse

    private val _createResourcesEventResponse = MutableLiveData<BaseResponse<ResourcesEventDto>>()
    val createResourcesEventResponse: LiveData<BaseResponse<ResourcesEventDto>> = _createResourcesEventResponse

    private val _editResourcesEventResponse = MutableLiveData<BaseResponse<ResourcesEventDto>>()
    val editResourcesEventResponse: LiveData<BaseResponse<ResourcesEventDto>> = _editResourcesEventResponse

    private val _getResourcesEventByGIDResponse = MutableLiveData<BaseResponse<ResourcesEventDto>>()
    val getResourcesEventByGIDResponse: LiveData<BaseResponse<ResourcesEventDto>> = _getResourcesEventByGIDResponse

    private val _deleteResourcesEventByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteResourcesEventByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteResourcesEventByGIDResponse

    fun getAllResourcesEvents(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { resourcesEventRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllResourcesEventsResponse
        )
    }

    fun createResourcesEvent(createResourcesEventDto: CreateResourcesEventDto) {
        performRequest(
            request = { resourcesEventRepository.create(createResourcesEventDto) },
            responseLiveData = _createResourcesEventResponse
        )
    }

    fun editResourcesEvent(resourcesEventDto: ResourcesEventDto) {
        performRequest(
            request = { resourcesEventRepository.edit(resourcesEventDto) },
            responseLiveData = _editResourcesEventResponse
        )
    }

    fun getResourcesEventByGID(gid: String) {
        performRequest(
            request = { resourcesEventRepository.getByGID(gid) },
            responseLiveData = _getResourcesEventByGIDResponse
        )
    }

    fun deleteResourcesEventByGID(gid: String) {
        performRequest(
            request = { resourcesEventRepository.deleteByGID(gid) },
            responseLiveData = _deleteResourcesEventByGIDResponse
        )
    }
}