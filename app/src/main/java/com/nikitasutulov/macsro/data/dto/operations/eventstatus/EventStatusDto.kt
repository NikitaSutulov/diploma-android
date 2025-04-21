package com.nikitasutulov.macsro.data.dto.operations.eventstatus

import kotlinx.serialization.Serializable

@Serializable
data class EventStatusDto(
    val gid: String,
    val name: String
) : java.io.Serializable
