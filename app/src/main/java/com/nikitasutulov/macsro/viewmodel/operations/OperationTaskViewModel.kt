package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.operationtask.CreateOperationTaskDto
import com.nikitasutulov.macsro.data.dto.operations.operationtask.OperationTaskDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class OperationTaskViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.operationTaskApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<OperationTaskDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<OperationTaskDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<OperationTaskDto>>()
    val createResponse: LiveData<BaseResponse<OperationTaskDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<OperationTaskDto>>()
    val editResponse: LiveData<BaseResponse<OperationTaskDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<OperationTaskDto>>()
    val getByGIDResponse: LiveData<BaseResponse<OperationTaskDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { api.getAll(token, pageNumber, pageSize) },
            responseLiveData = _getAllResponse
        )
    }

    fun create(token: String, createDto: CreateOperationTaskDto) {
        performRequest(
            request = { api.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun edit(token: String, dto: OperationTaskDto) {
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