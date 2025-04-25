package com.nikitasutulov.macsro.viewmodel.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.CreateVolunteersEventsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.VolunteersEventsDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class VolunteersEventsViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.volunteersEventsApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<VolunteersEventsDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<VolunteersEventsDto>>> = _getAllResponse

    private val _getByVolunteerGIDResponse =
        MutableLiveData<BaseResponse<List<VolunteersEventsDto>>>()
    val getByVolunteerGIDResponse: LiveData<BaseResponse<List<VolunteersEventsDto>>> =
        _getByVolunteerGIDResponse

    private val _getByEventGIDResponse =
        MutableLiveData<BaseResponse<List<VolunteersEventsDto>>>()
    val getByEventGIDResponse: LiveData<BaseResponse<List<VolunteersEventsDto>>> =
        _getByEventGIDResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<VolunteersEventsDto>>()
    val getByGIDResponse: LiveData<BaseResponse<VolunteersEventsDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    private val _createResponse = MutableLiveData<BaseResponse<VolunteersEventsDto>>()
    val createResponse: LiveData<BaseResponse<VolunteersEventsDto>> = _createResponse

    private val _existsResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val existsResponse: LiveData<BaseResponse<ResponseBody>> = _existsResponse

    fun getByVolunteerGID(token: String, volunteerGID: String) {
        performRequest(
            request = { api.getByVolunteerGID(token, volunteerGID) },
            responseLiveData = _getByVolunteerGIDResponse
        )
    }

    fun getByEventGID(token: String, eventGID: String) {
        performRequest(
            request = { api.getByEventGID(token, eventGID) },
            responseLiveData = _getByEventGIDResponse
        )
    }

    fun getByGID(token: String, gid: String) {
        performRequest(
            request = { api.getByGID(token, gid) },
            responseLiveData = _getByGIDResponse
        )
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(
            request = { api.deleteByGID(token, gid) },
            responseLiveData = _deleteByGIDResponse
        )
    }

    fun create(token: String, createDto: CreateVolunteersEventsDto) {
        performRequest(
            request = { api.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun exists(token: String, dto: CreateVolunteersEventsDto) {
        performRequest(
            request = { api.exists(token, dto) },
            responseLiveData = _existsResponse
        )
    }
}