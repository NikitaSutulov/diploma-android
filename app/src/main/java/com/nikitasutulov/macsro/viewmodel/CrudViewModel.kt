package com.nikitasutulov.macsro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.repository.CrudRepository
import okhttp3.ResponseBody

abstract class CrudViewModel<EntityDto, CreateEntityDto>
    (private val repository: CrudRepository<EntityDto, CreateEntityDto>) : ApiClientViewModel() {
    private val _getAllResponse = MutableLiveData<BaseResponse<List<EntityDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<EntityDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<EntityDto>>()
    val createResponse: LiveData<BaseResponse<EntityDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<EntityDto>>()
    val editResponse: LiveData<BaseResponse<EntityDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<EntityDto>>()
    val getByGIDResponse: LiveData<BaseResponse<EntityDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { repository.getAll(token, pageNumber, pageSize) },
            responseLiveData = _getAllResponse
        )
    }

    fun create(token: String, createDto: CreateEntityDto) {
        performRequest(
            request = { repository.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun edit(token: String, dto: EntityDto) {
        performRequest(
            request = { repository.edit(token, dto) },
            responseLiveData = _editResponse
        )
    }

    fun getByGID(token: String, gid: String) {
        performRequest(
            request = { repository.getByGID(token, gid) },
            responseLiveData = _getByGIDResponse
        )
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(
            request = { repository.deleteByGID(token, gid) },
            responseLiveData = _deleteByGIDResponse
        )
    }
}
