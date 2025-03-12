package com.nikitasutulov.macsro.viewmodel.operations.operationworker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.operationworker.CreateOperationWorkerDto
import com.nikitasutulov.macsro.data.dto.operations.operationworker.OperationWorkerDto
import com.nikitasutulov.macsro.repository.operations.OperationWorkerRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class OperationWorkerViewModel(private val operationWorkerRepository: OperationWorkerRepository): ViewModel() {
    private val _getAllOperationWorkersResponse = MutableLiveData<BaseResponse<List<OperationWorkerDto>>>()
    val getAllOperationWorkersResponse: LiveData<BaseResponse<List<OperationWorkerDto>>> = _getAllOperationWorkersResponse

    private val _createOperationWorkerResponse = MutableLiveData<BaseResponse<OperationWorkerDto>>()
    val createOperationWorkerResponse: LiveData<BaseResponse<OperationWorkerDto>> = _createOperationWorkerResponse

    private val _editOperationWorkerResponse = MutableLiveData<BaseResponse<OperationWorkerDto>>()
    val editOperationWorkerResponse: LiveData<BaseResponse<OperationWorkerDto>> = _editOperationWorkerResponse

    private val _getOperationWorkerByGIDResponse = MutableLiveData<BaseResponse<OperationWorkerDto>>()
    val getOperationWorkerByGIDResponse: LiveData<BaseResponse<OperationWorkerDto>> = _getOperationWorkerByGIDResponse

    private val _deleteOperationWorkerByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteOperationWorkerByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteOperationWorkerByGIDResponse

    fun getAllOperationWorkers(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { operationWorkerRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllOperationWorkersResponse
        )
    }

    fun createOperationWorker(createOperationWorkerDto: CreateOperationWorkerDto) {
        performRequest(
            request = { operationWorkerRepository.create(createOperationWorkerDto) },
            responseLiveData = _createOperationWorkerResponse
        )
    }

    fun editOperationWorker(operationWorkerDto: OperationWorkerDto) {
        performRequest(
            request = { operationWorkerRepository.edit(operationWorkerDto) },
            responseLiveData = _editOperationWorkerResponse
        )
    }

    fun getOperationWorkerByGID(gid: String) {
        performRequest(
            request = { operationWorkerRepository.getByGID(gid) },
            responseLiveData = _getOperationWorkerByGIDResponse
        )
    }

    fun deleteOperationWorkerByGID(gid: String) {
        performRequest(
            request = { operationWorkerRepository.deleteByGID(gid) },
            responseLiveData = _deleteOperationWorkerByGIDResponse
        )
    }
}