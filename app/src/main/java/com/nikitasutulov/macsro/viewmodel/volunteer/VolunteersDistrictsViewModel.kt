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

    private val _getAllResponse = MutableLiveData<BaseResponse<List<VolunteersDistrictsDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<VolunteersDistrictsDto>>> = _getAllResponse

    private val _getByVolunteerGIDResponse =
        MutableLiveData<BaseResponse<List<VolunteersDistrictsDto>>>()
    val getByVolunteerGIDResponse: LiveData<BaseResponse<List<VolunteersDistrictsDto>>> =
        _getByVolunteerGIDResponse

    private val _getByDistrictGIDResponse =
        MutableLiveData<BaseResponse<List<VolunteersDistrictsDto>>>()
    val getByDistrictGIDResponse: LiveData<BaseResponse<List<VolunteersDistrictsDto>>> =
        _getByDistrictGIDResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<VolunteersDistrictsDto>>()
    val getByGIDResponse: LiveData<BaseResponse<VolunteersDistrictsDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    private val _createResponse = MutableLiveData<BaseResponse<VolunteersDistrictsDto>>()
    val createResponse: LiveData<BaseResponse<VolunteersDistrictsDto>> = _createResponse

    private val _existsResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val existsResponse: LiveData<BaseResponse<ResponseBody>> = _existsResponse

    fun getByVolunteerGID(token: String, volunteerGID: String) {
        performRequest(
            request = { api.getByVolunteerGID(token, volunteerGID) },
            responseLiveData = _getByVolunteerGIDResponse
        )
    }

    fun getByDistrictGID(token: String, districtGID: String) {
        performRequest(
            request = { api.getByDistrictGID(token, districtGID) },
            responseLiveData = _getByDistrictGIDResponse
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

    fun create(token: String, createDto: CreateVolunteersDistrictsDto) {
        performRequest(
            request = { api.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun exists(token: String, dto: CreateVolunteersDistrictsDto) {
        performRequest(
            request = { api.exists(token, dto) },
            responseLiveData = _existsResponse
        )
    }
}