package com.nikitasutulov.macsro.viewmodel.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class DistrictViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.districtApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<DistrictDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<DistrictDto>>> = _getAllResponse

    fun getAll(token: String, pageNumber: Int? = null, pageSize: Int? = null) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }
}
