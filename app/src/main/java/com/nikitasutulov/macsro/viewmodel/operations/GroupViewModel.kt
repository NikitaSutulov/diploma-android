package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.group.CreateGroupDto
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class GroupViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.groupApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<GroupDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<GroupDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<GroupDto>>()
    val createResponse: LiveData<BaseResponse<GroupDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<GroupDto>>()
    val editResponse: LiveData<BaseResponse<GroupDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<GroupDto>>()
    val getByGIDResponse: LiveData<BaseResponse<GroupDto>> = _getByGIDResponse

    private val _getByEventGIDResponse = MutableLiveData<BaseResponse<List<GroupDto>>>()
    val getByEventGIDResponse: LiveData<BaseResponse<List<GroupDto>>> = _getByEventGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun create(token: String, createDto: CreateGroupDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun edit(token: String, dto: GroupDto) {
        performRequest(_editResponse) { api.edit(token, dto) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun getByEventGID(token: String, eventGID: String) {
        performRequest(_getByEventGIDResponse) { api.getByEventGID(token, eventGID) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }
}
