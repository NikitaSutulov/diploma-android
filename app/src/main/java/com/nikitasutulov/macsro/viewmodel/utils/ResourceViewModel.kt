package com.nikitasutulov.macsro.viewmodel.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.resource.CreateResourceDto
import com.nikitasutulov.macsro.data.dto.utils.resource.ResourceDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class ResourceViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.resourceApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<ResourceDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<ResourceDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<ResourceDto>>()
    val createResponse: LiveData<BaseResponse<ResourceDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<ResourceDto>>()
    val editResponse: LiveData<BaseResponse<ResourceDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<ResourceDto>>()
    val getByGIDResponse: LiveData<BaseResponse<ResourceDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun create(token: String, createDto: CreateResourceDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun edit(token: String, dto: ResourceDto) {
        performRequest(_editResponse) { api.edit(token, dto) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }
}
