package com.nikitasutulov.macsro.viewmodel.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class ResourceViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.resourceApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<ResourceDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<ResourceDto>>> = _getAllResponse

    fun getAll(token: String, pageNumber: Int? = null, pageSize: Int? = null) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }
}
