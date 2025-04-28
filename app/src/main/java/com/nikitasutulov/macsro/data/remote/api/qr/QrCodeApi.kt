package com.nikitasutulov.macsro.data.remote.api.qr

import com.nikitasutulov.macsro.data.dto.qr.QrCodeResponseDto
import com.nikitasutulov.macsro.data.dto.volunteer.volunteersevents.CreateVolunteersEventsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface QrCodeApi {
    @POST("QrCode/generate-qr")
    suspend fun generateQrCode(
        @Header("Authorization") token: String,
        @Body dto: CreateVolunteersEventsDto
    ): Response<QrCodeResponseDto>
}
