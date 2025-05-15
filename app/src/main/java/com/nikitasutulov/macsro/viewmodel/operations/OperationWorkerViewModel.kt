package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.operationworker.OperationWorkerDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class OperationWorkerViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.operationWorkerApi

    private val _getByUserGIDResponse = MutableLiveData<BaseResponse<OperationWorkerDto>>()
    val getByUserGIDResponse: LiveData<BaseResponse<OperationWorkerDto>> = _getByUserGIDResponse

    fun getByUserGID(token: String, userGID: String) {
        performRequest(_getByUserGIDResponse) { api.getByUserGID(token, userGID) }
    }
}
