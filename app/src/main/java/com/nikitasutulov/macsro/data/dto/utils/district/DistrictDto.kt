package com.nikitasutulov.macsro.data.dto.utils.district

import kotlinx.serialization.Serializable

@Serializable
data class DistrictDto(
    val gid: String,
    val name: String
) : java.io.Serializable
