package com.nikitasutulov.macsro.viewmodel.district

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.district.CreateDistrictDto
import com.nikitasutulov.macsro.data.dto.district.DistrictDto
import com.nikitasutulov.macsro.repository.DistrictRepository
import com.nikitasutulov.macsro.util.performRequest
import okhttp3.ResponseBody

class DistrictViewModel(private val districtRepository: DistrictRepository): ViewModel() {
    private val _getAllDistrictsResponse = MutableLiveData<BaseResponse<List<DistrictDto>>>()
    val getAllDistrictsResponse: LiveData<BaseResponse<List<DistrictDto>>> = _getAllDistrictsResponse

    private val _createDistrictResponse = MutableLiveData<BaseResponse<DistrictDto>>()
    val createDistrictResponse: LiveData<BaseResponse<DistrictDto>> = _createDistrictResponse

    private val _editDistrictResponse = MutableLiveData<BaseResponse<DistrictDto>>()
    val editDistrictResponse: LiveData<BaseResponse<DistrictDto>> = _editDistrictResponse

    private val _getDistrictByGIDResponse = MutableLiveData<BaseResponse<DistrictDto>>()
    val getDistrictByGIDResponse: LiveData<BaseResponse<DistrictDto>> = _getDistrictByGIDResponse

    private val _deleteDistrictByGIDResponse = MutableLiveData<BaseResponse<ResponseBody>>()
    val deleteDistrictByGIDResponse: LiveData<BaseResponse<ResponseBody>> = _deleteDistrictByGIDResponse

    fun getAllDistricts(pageNumber: Int?, pageSize: Int?) {
        performRequest(
            request = { districtRepository.getAll(pageNumber, pageSize) },
            responseLiveData = _getAllDistrictsResponse
        )
    }

    fun createDistrict(name: String) {
        performRequest(
            request = { districtRepository.create(CreateDistrictDto(name)) },
            responseLiveData = _createDistrictResponse
        )
    }

    fun editDistrict(gid: String, name: String) {
        performRequest(
            request = { districtRepository.edit(DistrictDto(gid, name)) },
            responseLiveData = _editDistrictResponse
        )
    }

    fun getDistrictByGID(gid: String) {
        performRequest(
            request = { districtRepository.getByGID(gid) },
            responseLiveData = _getDistrictByGIDResponse
        )
    }

    fun deleteDistrictByGID(gid: String) {
        performRequest(
            request = { districtRepository.deleteByGID(gid) },
            responseLiveData = _deleteDistrictByGIDResponse
        )
    }
}