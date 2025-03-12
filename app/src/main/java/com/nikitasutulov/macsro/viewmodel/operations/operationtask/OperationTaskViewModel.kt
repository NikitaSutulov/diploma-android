package com.nikitasutulov.macsro.viewmodel.operations.operationtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.operationtask.CreateOperationTaskDto
import com.nikitasutulov.macsro.data.dto.operations.operationtask.OperationTaskDto
import com.nikitasutulov.macsro.repository.operations.OperationTaskRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class OperationTaskViewModel(private val operationTaskRepository: OperationTaskRepository): ViewModel() {
    private val _getAllOperationTasksResponse = MutableLiveData<BaseResponse<List<OperationTaskDto>>>()
    val getAllOperationTasksResponse: LiveData<BaseResponse<List<OperationTaskDto>>> = _getAllOperationTasksResponse

    private val _createOperationTaskResponse = MutableLiveData<BaseResponse<OperationTaskDto>>()
    val createOperationTaskResponse: LiveData<BaseResponse<OperationTaskDto>> = _createOperationTaskResponse

    private val _editOperationTaskResponse = MutableLiveData<BaseResponse<OperationTaskDto>>()
    val editOperationTaskResponse: LiveData<BaseResponse<OperationTaskDto>> = _editOperationTaskResponse

    private val _getOperationTaskByGIDResponse = MutableLiveData<BaseResponse<OperationTaskDto>>()
    val getOperationTaskByGIDResponse: LiveData<BaseResponse<OperationTaskDto>> = _getOperationTaskByGIDResponse

    private val _deleteOperationTaskByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteOperationTaskByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteOperationTaskByGIDResponse

    fun getAllOperationTasks(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { operationTaskRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllOperationTasksResponse
        )
    }

    fun createOperationTask(createOperationTaskDto: CreateOperationTaskDto) {
        performRequest(
            request = { operationTaskRepository.create(createOperationTaskDto) },
            responseLiveData = _createOperationTaskResponse
        )
    }

    fun editOperationTask(operationTaskDto: OperationTaskDto) {
        performRequest(
            request = { operationTaskRepository.edit(operationTaskDto) },
            responseLiveData = _editOperationTaskResponse
        )
    }

    fun getOperationTaskByGID(gid: String) {
        performRequest(
            request = { operationTaskRepository.getByGID(gid) },
            responseLiveData = _getOperationTaskByGIDResponse
        )
    }

    fun deleteOperationTaskByGID(gid: String) {
        performRequest(
            request = { operationTaskRepository.deleteByGID(gid) },
            responseLiveData = _deleteOperationTaskByGIDResponse
        )
    }
}