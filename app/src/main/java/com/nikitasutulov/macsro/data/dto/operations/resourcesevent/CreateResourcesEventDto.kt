package com.nikitasutulov.macsro.data.dto.operations.resourcesevent

data class CreateResourcesEventDto(
    val resourceGID: String,
    val eventGID: String,
    val requiredQuantity: Int,
    val availableQuantity: Int
)
