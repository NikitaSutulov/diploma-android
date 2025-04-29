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
        performRequest(_getByVolunteerGIDResponse) { api.getByVolunteerGID(token, volunteerGID) }
    }

    fun getByDistrictGID(token: String, districtGID: String) {
        performRequest(_getByDistrictGIDResponse) { api.getByDistrictGID(token, districtGID) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }

    fun create(token: String, createDto: CreateVolunteersDistrictsDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun exists(token: String, dto: CreateVolunteersDistrictsDto) {
        performRequest(_existsResponse) { api.exists(token, dto) }
    }
}
