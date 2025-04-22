package com.nikitasutulov.macsro.viewmodel.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class VolunteerViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.volunteerApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<VolunteerDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<VolunteerDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<VolunteerDto>>()
    val createResponse: LiveData<BaseResponse<VolunteerDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<VolunteerDto>>()
    val editResponse: LiveData<BaseResponse<VolunteerDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<VolunteerDto>>()
    val getByGIDResponse: LiveData<BaseResponse<VolunteerDto>> = _getByGIDResponse

    private val _getByUserGIDResponse = MutableLiveData<BaseResponse<VolunteerDto>>()
    val getByUserGIDResponse: LiveData<BaseResponse<VolunteerDto>> = _getByUserGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { api.getAll(token, pageNumber, pageSize) },
            responseLiveData = _getAllResponse
        )
    }

    fun create(token: String, createDto: CreateVolunteerDto) {
        performRequest(
            request = { api.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun edit(token: String, dto: VolunteerDto) {
        performRequest(
            request = { api.edit(token, dto) },
            responseLiveData = _editResponse
        )
    }

    fun getByGID(token: String, gid: String) {
        performRequest(
            request = { api.getByGID(token, gid) },
            responseLiveData = _getByGIDResponse
        )
    }

    fun getByUserGID(token: String, userGID: String) {
        performRequest(
            request = { api.getByUserGID(token, userGID) },
            responseLiveData = _getByUserGIDResponse
        )
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(
            request = { api.deleteByGID(token, gid) },
            responseLiveData = _deleteByGIDResponse
        )
    }
}