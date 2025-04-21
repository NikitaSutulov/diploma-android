package com.nikitasutulov.macsro.data.dto.operations.eventtype

import kotlinx.serialization.Serializable

@Serializable
data class EventTypeDto(
    val gid: String,
    val name: String
) : java.io.Serializable
