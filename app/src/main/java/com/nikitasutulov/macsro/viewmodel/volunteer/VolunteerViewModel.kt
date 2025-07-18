package com.nikitasutulov.macsro.viewmodel.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.CreateVolunteerDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.RatingPositionDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.UpdateRatingDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteer.VolunteerDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class VolunteerViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.volunteerApi

    private val _createResponse = MutableLiveData<BaseResponse<VolunteerDto>>()
    val createResponse: LiveData<BaseResponse<VolunteerDto>> = _createResponse

    private val _getRatingPositionResponse = MutableLiveData<BaseResponse<RatingPositionDto>>()
    val getRatingPositionResponse: LiveData<BaseResponse<RatingPositionDto>> = _getRatingPositionResponse

    private val _getByUserGIDResponse = MutableLiveData<BaseResponse<VolunteerDto>>()
    val getByUserGIDResponse: LiveData<BaseResponse<VolunteerDto>> = _getByUserGIDResponse

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

    fun getRatingPosition(token: String, volunteerGID: String) {
        performRequest(_getRatingPositionResponse) { api.getRatingPosition(token, volunteerGID) }
    }

    fun updateRating(token: String, updateRatingDto: UpdateRatingDto): LiveData<BaseResponse<ResponseBody>> {
        val responseMutableLiveData = MutableLiveData<BaseResponse<ResponseBody>>()
        val responseLiveData: LiveData<BaseResponse<ResponseBody>> = responseMutableLiveData
        performRequest(responseMutableLiveData) { api.updateRating(token, updateRatingDto) }
        return responseLiveData
    }

    fun getByGroupGID(token: String, groupGID: String): LiveData<BaseResponse<List<VolunteerDto>>> {
        val responseMutableLiveData = MutableLiveData<BaseResponse<List<VolunteerDto>>>()
        val responseLiveData: LiveData<BaseResponse<List<VolunteerDto>>> = responseMutableLiveData
        performRequest(responseMutableLiveData) { api.getByGroupGID(token, groupGID) }
        return responseLiveData
    }

    fun getByUserGID(token: String, userGID: String) {
        performRequest(_getByUserGIDResponse) { api.getByUserGID(token, userGID) }
    }
}
