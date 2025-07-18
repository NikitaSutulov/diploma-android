package com.nikitasutulov.macsro.data.dto.volunteer.volunteer

data class CreateVolunteerDto(
    val name: String,
    val surname: String,
    val secondName: String,
    val email: String,
    val mobilePhone: String,
    val ratingNumber: Int,
    val birthDate: String,
    val userGID: String
)
