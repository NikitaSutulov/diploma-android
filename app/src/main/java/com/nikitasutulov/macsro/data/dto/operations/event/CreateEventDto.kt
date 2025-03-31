package com.nikitasutulov.macsro.data.dto.operations.event

data class CreateEventDto(
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val eventTypeGID: String,
    val districtGID: String,
    val coordinatorGID: String,
    val dispatcherGID: String,
    val eventStatusGID: String
)
