package com.nikitasutulov.macsro.viewmodel.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.CreateVolunteersEventsDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.VolunteersEventsDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class VolunteersEventsViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.volunteersEventsApi

    private val _getByVolunteerGIDResponse =
        MutableLiveData<BaseResponse<List<VolunteersEventsDto>>>()
    val getByVolunteerGIDResponse: LiveData<BaseResponse<List<VolunteersEventsDto>>> =
        _getByVolunteerGIDResponse

    private val _createResponse = MutableLiveData<BaseResponse<VolunteersEventsDto>>()
    val createResponse: LiveData<BaseResponse<VolunteersEventsDto>> = _createResponse

    fun getByVolunteerGID(token: String, volunteerGID: String) {
        performRequest(_getByVolunteerGIDResponse) { api.getByVolunteerGID(token, volunteerGID) }
    }

    fun create(token: String, createDto: CreateVolunteersEventsDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }
}
