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

    private val _getByUserGIDResponse = MutableLiveData<BaseResponse<OperationWorkerDto>>()
    val getByUserGIDResponse: LiveData<BaseResponse<OperationWorkerDto>> = _getByUserGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun create(token: String, createDto: CreateOperationWorkerDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun edit(token: String, dto: OperationWorkerDto) {
        performRequest(_editResponse) { api.edit(token, dto) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun getByUserGID(token: String, userGID: String) {
        performRequest(_getByUserGIDResponse) { api.getByUserGID(token, userGID) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }
}
