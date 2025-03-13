package com.nikitasutulov.macsro.viewmodel.operations.operationtaskstatus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.CreateOperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
import com.nikitasutulov.macsro.repository.OperationTaskStatusRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class OperationTaskStatusViewModel(private val operationTaskStatusRepository: OperationTaskStatusRepository): ViewModel() {
    private val _getAllOperationTaskStatusesResponse = MutableLiveData<BaseResponse<List<OperationTaskStatusDto>>>()
    val getAllOperationTaskStatusesResponse: LiveData<BaseResponse<List<OperationTaskStatusDto>>> = _getAllOperationTaskStatusesResponse

    private val _createOperationTaskStatusResponse = MutableLiveData<BaseResponse<OperationTaskStatusDto>>()
    val createOperationTaskStatusResponse: LiveData<BaseResponse<OperationTaskStatusDto>> = _createOperationTaskStatusResponse

    private val _editOperationTaskStatusResponse = MutableLiveData<BaseResponse<OperationTaskStatusDto>>()
    val editOperationTaskStatusResponse: LiveData<BaseResponse<OperationTaskStatusDto>> = _editOperationTaskStatusResponse

    private val _getOperationTaskStatusByGIDResponse = MutableLiveData<BaseResponse<OperationTaskStatusDto>>()
    val getOperationTaskStatusByGIDResponse: LiveData<BaseResponse<OperationTaskStatusDto>> = _getOperationTaskStatusByGIDResponse

    private val _deleteOperationTaskStatusByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteOperationTaskStatusByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteOperationTaskStatusByGIDResponse

    fun getAllOperationTaskStatuses(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { operationTaskStatusRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllOperationTaskStatusesResponse
        )
    }

    fun createOperationTaskStatus(name: String) {
        performRequest(
            request = { operationTaskStatusRepository.create(CreateOperationTaskStatusDto(name)) },
            responseLiveData = _createOperationTaskStatusResponse
        )
    }

    fun editOperationTaskStatus(gid: String, name: String) {
        performRequest(
            request = { operationTaskStatusRepository.edit(OperationTaskStatusDto(gid, name)) },
            responseLiveData = _editOperationTaskStatusResponse
        )
    }

    fun getOperationTaskStatusByGID(gid: String) {
        performRequest(
            request = { operationTaskStatusRepository.getByGID(gid) },
            responseLiveData = _getOperationTaskStatusByGIDResponse
        )
    }

    fun deleteOperationTaskStatusByGID(gid: String) {
        performRequest(
            request = { operationTaskStatusRepository.deleteByGID(gid) },
            responseLiveData = _deleteOperationTaskStatusByGIDResponse
        )
    }
}