package com.nikitasutulov.macsro.data.dto.operations.resourcesevent

data class ResourcesEventDto(
    val gid: String,
    val resourceGID: String,
    val eventGID: String,
    val requiredQuantity: Double,
    val availableQuantity: Double
)
