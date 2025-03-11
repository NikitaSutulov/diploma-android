package com.nikitasutulov.macsro.viewmodel.utils.measurementunit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.CreateMeasurementUnitDto
import com.nikitasutulov.macsro.data.dto.utils.measurementunit.MeasurementUnitDto
import com.nikitasutulov.macsro.repository.utils.MeasurementUnitRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class MeasurementUnitViewModel(private val measurementUnitRepository: MeasurementUnitRepository): ViewModel() {
    private val _getAllMeasurementUnitsResponse = MutableLiveData<BaseResponse<List<MeasurementUnitDto>>>()
    val getAllMeasurementUnitsResponse: LiveData<BaseResponse<List<MeasurementUnitDto>>> = _getAllMeasurementUnitsResponse

    private val _createMeasurementUnitResponse = MutableLiveData<BaseResponse<MeasurementUnitDto>>()
    val createMeasurementUnitResponse: LiveData<BaseResponse<MeasurementUnitDto>> = _createMeasurementUnitResponse

    private val _editMeasurementUnitResponse = MutableLiveData<BaseResponse<MeasurementUnitDto>>()
    val editMeasurementUnitResponse: LiveData<BaseResponse<MeasurementUnitDto>> = _editMeasurementUnitResponse

    private val _getMeasurementUnitByGIDResponse = MutableLiveData<BaseResponse<MeasurementUnitDto>>()
    val getMeasurementUnitByGIDResponse: LiveData<BaseResponse<MeasurementUnitDto>> = _getMeasurementUnitByGIDResponse

    private val _deleteMeasurementUnitByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteMeasurementUnitByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteMeasurementUnitByGIDResponse

    fun getAllMeasurementUnits(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { measurementUnitRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllMeasurementUnitsResponse
        )
    }

    fun createMeasurementUnit(name: String) {
        performRequest(
            request = { measurementUnitRepository.create(CreateMeasurementUnitDto(name)) },
            responseLiveData = _createMeasurementUnitResponse
        )
    }

    fun editMeasurementUnit(gid: String, name: String) {
        performRequest(
            request = { measurementUnitRepository.edit(MeasurementUnitDto(gid, name)) },
            responseLiveData = _editMeasurementUnitResponse
        )
    }

    fun getMeasurementUnitByGID(gid: String) {
        performRequest(
            request = { measurementUnitRepository.getByGID(gid) },
            responseLiveData = _getMeasurementUnitByGIDResponse
        )
    }

    fun deleteMeasurementUnitByGID(gid: String) {
        performRequest(
            request = { measurementUnitRepository.deleteByGID(gid) },
            responseLiveData = _deleteMeasurementUnitByGIDResponse
        )
    }
}