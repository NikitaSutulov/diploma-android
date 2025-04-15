package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.operationworker.CreateOperationWorkerDto
import com.nikitasutulov.macsro.data.dto.operations.operationworker.OperationWorkerDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class OperationWorkerViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.operationWorkerApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<OperationWorkerDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<OperationWorkerDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<OperationWorkerDto>>()
    val createResponse: LiveData<BaseResponse<OperationWorkerDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<OperationWorkerDto>>()
    val editResponse: LiveData<BaseResponse<OperationWorkerDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<OperationWorkerDto>>()
    val getByGIDResponse: LiveData<BaseResponse<OperationWorkerDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { api.getAll(token, pageNumber, pageSize) },
            responseLiveData = _getAllResponse
        )
    }

    fun create(token: String, createDto: CreateOperationWorkerDto) {
        performRequest(
            request = { api.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun edit(token: String, dto: OperationWorkerDto) {
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