package com.nikitasutulov.macsro.viewmodel.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.CreateMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.MeasurementUnitDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel
import okhttp3.ResponseBody

class MeasurementUnitViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.measurementUnitApi

    private val _getAllResponse = MutableLiveData<BaseResponse<List<MeasurementUnitDto>>>()
    val getAllResponse: LiveData<BaseResponse<List<MeasurementUnitDto>>> = _getAllResponse

    private val _createResponse = MutableLiveData<BaseResponse<MeasurementUnitDto>>()
    val createResponse: LiveData<BaseResponse<MeasurementUnitDto>> = _createResponse

    private val _editResponse = MutableLiveData<BaseResponse<MeasurementUnitDto>>()
    val editResponse: LiveData<BaseResponse<MeasurementUnitDto>> = _editResponse

    private val _getByGIDResponse = MutableLiveData<BaseResponse<MeasurementUnitDto>>()
    val getByGIDResponse: LiveData<BaseResponse<MeasurementUnitDto>> = _getByGIDResponse

    private val _deleteByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteByGIDResponse

    fun getAll(token: String, pageNumber: Int?, pageSize: Int?) {
        performRequest(_getAllResponse) { api.getAll(token, pageNumber, pageSize) }
    }

    fun create(token: String, createDto: CreateMeasurementUnitDto) {
        performRequest(_createResponse) { api.create(token, createDto) }
    }

    fun edit(token: String, dto: MeasurementUnitDto) {
        performRequest(_editResponse) { api.edit(token, dto) }
    }

    fun getByGID(token: String, gid: String) {
        performRequest(_getByGIDResponse) { api.getByGID(token, gid) }
    }

    fun deleteByGID(token: String, gid: String) {
        performRequest(_deleteByGIDResponse) { api.deleteByGID(token, gid) }
    }
}
