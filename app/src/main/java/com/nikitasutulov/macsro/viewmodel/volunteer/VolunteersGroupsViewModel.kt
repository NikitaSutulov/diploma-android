package com.nikitasutulov.macsro.viewmodel.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.CreateVolunteersGroupsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class VolunteersGroupsViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.volunteersGroupsApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<VolunteersGroupsDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<VolunteersGroupsDto>>> = _getAllResponse

    private val _getByVolunteerGIDResponse =
        MutableLiveData<BaseResponse<List<VolunteersGroupsDto>>>()
    val getByVolunteerGIDResponse: LiveData<BaseResponse<List<VolunteersGroupsDto>>> =
        _getByVolunteerGIDResponse

    private val _getByGroupGIDResponse =
        MutableLiveData<BaseResponse<List<VolunteersGroupsDto>>>()
    val getByGroupGIDResponse: LiveData<BaseResponse<List<VolunteersGroupsDto>>> =
        _getByGroupGIDResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<VolunteersGroupsDto>>()
    val getByGIDResponse: LiveData<BaseResponse<VolunteersGroupsDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    private val _createResponse = MutableLiveData<BaseResponse<VolunteersGroupsDto>>()
    val createResponse: LiveData<BaseResponse<VolunteersGroupsDto>> = _createResponse

    private val _existsResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val existsResponse: LiveData<BaseResponse<ResponseBody>> = _existsResponse

    fun getByVolunteerGID(token: String, volunteerGID: String) {
        performRequest(_getByVolunteerGIDResponse) { api.getByVolunteerGID(token, volunteerGID) }
    }

    fun getByGroupGID(token: String, groupGID: String) {
        performRequest(_getByGroupGIDResponse) { api.getByGroupGID(token, groupGID) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }

    fun create(token: String, createDto: CreateVolunteersGroupsDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun exists(token: String, dto: CreateVolunteersGroupsDto) {
        performRequest(_existsResponse) { api.exists(token, dto) }
    }
}
