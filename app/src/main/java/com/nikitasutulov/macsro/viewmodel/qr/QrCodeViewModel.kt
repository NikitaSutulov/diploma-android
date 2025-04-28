package com.nikitasutulov.macsro.viewmodel.qr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitasutulov.macsro.data.dto.BaseResponse
import com.nikitasutulov.macsro.data.dto.qr.QrCodeResponseDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.CreateVolunteersEventsDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import com.nikitasutulov.macsro.viewmodel.ApiClientViewModel

class QrCodeViewModel : ApiClientViewModel() {
    private val api = RetrofitClient.qrCodeApi

    private val _generateQrCodeResponse = MutableLiveData<BaseResponse<QrCodeResponseDto>>()
    val generateQrCodeResponse: LiveData<BaseResponse<QrCodeResponseDto>> = _generateQrCodeResponse

    fun generateQrCode(token: String, dto: CreateVolunteersEventsDto) {
        performRequest(
            request = { api.generateQrCode(token, dto) },
            responseLiveData = _generateQrCodeResponse
        )
    }
}
