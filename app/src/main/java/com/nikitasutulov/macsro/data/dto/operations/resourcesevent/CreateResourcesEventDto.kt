package com.nikitasutulov.macsro.data.dto.operations.resourcesevent

data class CreateResourcesEventDto(
    val resourceGID: String,
    val eventGID: String,
    val measurementUnitGID: String,
    val requiredQuantity: Double,
    val availableQuantity: Double
)
