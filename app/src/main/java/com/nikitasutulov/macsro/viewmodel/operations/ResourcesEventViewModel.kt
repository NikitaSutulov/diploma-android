package com.nikitasutulov.macsro.viewmodel.operations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class ResourcesEventViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.resourcesEventApi

    private val _getByEventGIDResponse = MutableLiveData<BaseResponse<List<ResourcesEventDto>>>()
    val getByEventGIDResponse: LiveData<BaseResponse<List<ResourcesEventDto>>> = _getByEventGIDResponse

    fun getByEventGID(token: String, eventGID: String) {
        performRequest(_getByEventGIDResponse) { api.getByEventGID(token, eventGID) }
    }
}
