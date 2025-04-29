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
        performRequest(_getByVolunteerGIDResponse) { api.getByVolunteerGID(token, volunteerGID) }
    }

    fun getByEventGID(token: String, eventGID: String) {
        performRequest(_getByEventGIDResponse) { api.getByEventGID(token, eventGID) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }

    fun create(token: String, createDto: CreateVolunteersEventsDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun exists(token: String, dto: CreateVolunteersEventsDto) {
        performRequest(_existsResponse) { api.exists(token, dto) }
    }
}
