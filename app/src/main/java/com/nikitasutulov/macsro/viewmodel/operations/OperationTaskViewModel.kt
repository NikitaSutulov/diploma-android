package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.operationtask.OperationTaskDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class OperationTaskViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.operationTaskApi

    fun getByGroupGID(token: String, groupGID: String): LiveData<BaseResponse<List<OperationTaskDto>>> {
        val responseMutableLiveData = MutableLiveData<BaseResponse<List<OperationTaskDto>>>()
        val responseLiveData: LiveData<BaseResponse<List<OperationTaskDto>>> = responseMutableLiveData
        performRequest(responseMutableLiveData) { api.getByGroupGID(token, groupGID) }
        return responseLiveData
    }
}
