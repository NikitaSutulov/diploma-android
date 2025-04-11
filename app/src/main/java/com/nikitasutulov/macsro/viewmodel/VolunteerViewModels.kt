package com.nikitasutulov.macsro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.CreateVolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersdistricts.VolunteersDistrictsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.CreateVolunteersGroupsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import com.nikitasutulov.macsro.repository.VolunteerRepository
import com.nikitasutulov.macsro.repository.VolunteersDistrictsRepository
import com.nikitasutulov.macsro.repository.VolunteersGroupsRepository
import okhttp3.ResponseBody

class VolunteerViewModel(repository: VolunteerRepository) :
    CrudViewModel<VolunteerDto, CreateVolunteerDto>(repository)

class VolunteersDistrictsViewModel(private val repository: VolunteersDistrictsRepository) : ApiClientViewModel() {
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
            request = { repository.getByVolunteerGID(token, volunteerGID) },
            responseLiveData = _getByVolunteerGIDResponse
        )
    }

    fun getByDistrictGID(token: String, districtGID: String) {
        performRequest(
            request = { repository.getByDistrictGID(token, districtGID) },
            responseLiveData = _getByDistrictGIDResponse
        )
    }

    fun getByGID(token: String, gid: String) {
        performRequest(
            request = { repository.getByGID(token, gid) },
            responseLiveData = _getByGIDResponse
        )
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(
            request = { repository.deleteByGID(token, gid) },
            responseLiveData = _deleteByGIDResponse
        )
    }

    fun create(token: String, createDto: CreateVolunteersDistrictsDto) {
        performRequest(
            request = { repository.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun exists(token: String, dto: CreateVolunteersDistrictsDto) {
        performRequest(
            request = { repository.exists(token, dto) },
            responseLiveData = _existsResponse
        )
    }
}

class VolunteersGroupsViewModel(private val repository: VolunteersGroupsRepository) : ApiClientViewModel() {
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
        performRequest(
            request = { repository.getByVolunteerGID(token, volunteerGID) },
            responseLiveData = _getByVolunteerGIDResponse
        )
    }

    fun getByGroupGID(token: String, groupGID: String) {
        performRequest(
            request = { repository.getByGroupGID(token, groupGID) },
            responseLiveData = _getByGroupGIDResponse
        )
    }

    fun getByGID(token: String, gid: String) {
        performRequest(
            request = { repository.getByGID(token, gid) },
            responseLiveData = _getByGIDResponse
        )
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(
            request = { repository.deleteByGID(token, gid) },
            responseLiveData = _deleteByGIDResponse
        )
    }

    fun create(token: String, createDto: CreateVolunteersGroupsDto) {
        performRequest(
            request = { repository.create(token, createDto) },
            responseLiveData = _createResponse
        )
    }

    fun exists(token: String, dto: CreateVolunteersGroupsDto) {
        performRequest(
            request = { repository.exists(token, dto) },
            responseLiveData = _existsResponse
        )
    }
}