package com.nikitasutulov.macsro.viewmodel.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersgroups.VolunteersGroupsDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class VolunteersGroupsViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.volunteersGroupsApi

    private val _getByVolunteerGIDResponse =
        MutableLiveData<BaseResponse<List<VolunteersGroupsDto>>>()
    val getByVolunteerGIDResponse: LiveData<BaseResponse<List<VolunteersGroupsDto>>> =
        _getByVolunteerGIDResponse

    private val _getByGroupGIDResponse =
        MutableLiveData<BaseResponse<List<VolunteersGroupsDto>>>()
    val getByGroupGIDResponse: LiveData<BaseResponse<List<VolunteersGroupsDto>>> =
        _getByGroupGIDResponse

    fun getByVolunteerGID(token: String, volunteerGID: String) {
        performRequest(_getByVolunteerGIDResponse) { api.getByVolunteerGID(token, volunteerGID) }
    }

    fun getByGroupGID(token: String, groupGID: String) {
        performRequest(_getByGroupGIDResponse) { api.getByGroupGID(token, groupGID) }
    }
}
