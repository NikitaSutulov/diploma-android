package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class OperationTaskStatusViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.operationTaskStatusApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<OperationTaskStatusDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<OperationTaskStatusDto>>> = _getAllResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }
}
