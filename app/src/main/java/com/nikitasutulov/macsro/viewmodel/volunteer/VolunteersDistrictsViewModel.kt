package com.nikitasutulov.macsro.viewmodel.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.CreateVolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.VolunteersDistrictsDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class VolunteersDistrictsViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.volunteersDistrictsApi

    private val _getByVolunteerGIDResponse =
        MutableLiveData<BaseResponse<List<VolunteersDistrictsDto>>>()
    val getByVolunteerGIDResponse: LiveData<BaseResponse<List<VolunteersDistrictsDto>>> =
        _getByVolunteerGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    private val _createResponse = MutableLiveData<BaseResponse<VolunteersDistrictsDto>>()
    val createResponse: LiveData<BaseResponse<VolunteersDistrictsDto>> = _createResponse

    fun getByVolunteerGID(token: String, volunteerGID: String) {
        performRequest(_getByVolunteerGIDResponse) { api.getByVolunteerGID(token, volunteerGID) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }

    fun create(token: String, createDto: CreateVolunteersDistrictsDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }
}
