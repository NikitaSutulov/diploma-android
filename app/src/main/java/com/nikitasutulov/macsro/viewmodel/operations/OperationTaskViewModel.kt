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

    private val _getByGroupGIDResponse = MutableLiveData<BaseResponse<List<OperationTaskDto>>>()
    val getByGroupGIDResponse: LiveData<BaseResponse<List<OperationTaskDto>>> = _getByGroupGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun create(token: String, createDto: CreateOperationTaskDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun edit(token: String, dto: OperationTaskDto) {
        performRequest(_editResponse) { api.edit(token, dto) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }

    fun getByGroupGID(token: String, groupGID: String) {
        performRequest(_getByGroupGIDResponse) { api.getByGroupGID(token, groupGID) }
    }
}
