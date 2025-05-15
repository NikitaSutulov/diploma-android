package com.nikitasutulov.macsro.viewmodel.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.MeasurementUnitDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class MeasurementUnitViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.measurementUnitApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<MeasurementUnitDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<MeasurementUnitDto>>> = _getAllResponse

    fun getAll(token: String, pageNumber: Int? = null, pageSize: Int? = null) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }
}
