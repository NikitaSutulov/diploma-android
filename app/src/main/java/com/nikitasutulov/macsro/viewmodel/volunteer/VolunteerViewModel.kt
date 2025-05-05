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

    fun getAll(
        token: String,
        pageNumber: Int? = null,
        pageSize: Int? = null,
        sortBy: String = "",
        isDescending: Boolean = false
    ): LiveData<BaseResponse<List<VolunteerDto>>> {
        val responseMutableLiveData = MutableLiveData<BaseResponse<List<VolunteerDto>>>()
        val responseLiveData: LiveData<BaseResponse<List<VolunteerDto>>> = responseMutableLiveData
        performRequest(responseMutableLiveData) {
            api.getAll(
                token,
                pageNumber,
                pageSize,
                sortBy,
                isDescending
            )
        }
        return responseLiveData
    }

    fun create(token: String, createDto: CreateVolunteerDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun edit(token: String, dto: VolunteerDto) {
        performRequest(_editResponse) { api.edit(token, dto) }
    }

    fun getByGroupGID(token: String, groupGID: String): LiveData<BaseResponse<List<VolunteerDto>>> {
        val responseMutableLiveData = MutableLiveData<BaseResponse<List<VolunteerDto>>>()
        val responseLiveData: LiveData<BaseResponse<List<VolunteerDto>>> = responseMutableLiveData
        performRequest(responseMutableLiveData) { api.getByGroupGID(token, groupGID) }
        return responseLiveData
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun getByUserGID(token: String, userGID: String) {
        performRequest(_getByUserGIDResponse) { api.getByUserGID(token, userGID) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }
}
