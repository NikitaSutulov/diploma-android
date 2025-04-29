package com.nikitasutulov.macsro.viewmodel.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.district.CreateDistrictDto
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class DistrictViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.districtApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<DistrictDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<DistrictDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<DistrictDto>>()
    val createResponse: LiveData<BaseResponse<DistrictDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<DistrictDto>>()
    val editResponse: LiveData<BaseResponse<DistrictDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<DistrictDto>>()
    val getByGIDResponse: LiveData<BaseResponse<DistrictDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun create(token: String, createDto: CreateDistrictDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun edit(token: String, dto: DistrictDto) {
        performRequest(_editResponse) { api.edit(token, dto) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }
}
