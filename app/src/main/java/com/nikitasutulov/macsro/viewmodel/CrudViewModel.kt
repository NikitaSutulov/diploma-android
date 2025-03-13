package com.nikitasutulov.macsro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.repository.CrudRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

abstract class CrudViewModel<EntityDto, CreateEntityDto>(private val repository: CrudRepository<EntityDto, CreateEntityDto>): ViewModel() {
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

    fun getAll(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { repository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllResponse
        )
    }

    fun create(createDto: CreateEntityDto) {
        performRequest(
            request = { repository.create(createDto) },
            responseLiveData = _createResponse
        )
    }

    fun edit(dto: EntityDto) {
        performRequest(
            request = { repository.edit(dto) },
            responseLiveData = _editResponse
        )
    }

    fun getByGID(gid: String) {
        performRequest(
            request = { repository.getByGID(gid) },
            responseLiveData = _getByGIDResponse
        )
    }

    fun deleteByGID(gid: String) {
        performRequest(
            request = { repository.deleteByGID(gid) },
            responseLiveData = _deleteByGIDResponse
        )
    }
}