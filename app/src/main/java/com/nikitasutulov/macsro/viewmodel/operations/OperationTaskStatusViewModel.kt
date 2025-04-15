package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.CreateOperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class OperationTaskStatusViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.operationTaskStatusApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<OperationTaskStatusDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<OperationTaskStatusDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<OperationTaskStatusDto>>()
    val createResponse: LiveData<BaseResponse<OperationTaskStatusDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<OperationTaskStatusDto>>()
    val editResponse: LiveData<BaseResponse<OperationTaskStatusDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<OperationTaskStatusDto>>()
    val getByGIDResponse: LiveData<BaseResponse<OperationTaskStatusDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { api.getAll(token, pageNumber, pageSize) },
            responseLiveData = _getAllResponse
        )
    }

    fun create(token: String, createDto: CreateOperationTaskStatusDto) {
        performRequest(
            request = { api.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun edit(token: String, dto: OperationTaskStatusDto) {
        performRequest(
            request = { api.edit(token, dto) },
            responseLiveData = _editResponse
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
}