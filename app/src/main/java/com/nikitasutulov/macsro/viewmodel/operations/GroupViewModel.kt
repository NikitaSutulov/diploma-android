package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class GroupViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.groupApi

    private val _getByGIDResponse = MutableLiveData<BaseResponse<GroupDto>>()
    val getByGIDResponse: LiveData<BaseResponse<GroupDto>> = _getByGIDResponse

    private val _getByEventGIDResponse = MutableLiveData<BaseResponse<List<GroupDto>>>()
    val getByEventGIDResponse: LiveData<BaseResponse<List<GroupDto>>> = _getByEventGIDResponse

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun getByEventGID(token: String, eventGID: String) {
        performRequest(_getByEventGIDResponse) { api.getByEventGID(token, eventGID) }
    }
}
