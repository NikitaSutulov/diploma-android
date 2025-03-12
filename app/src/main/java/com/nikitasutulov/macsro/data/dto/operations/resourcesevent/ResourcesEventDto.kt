package com.nikitasutulov.macsro.data.dto.operations.resourcesevent

data class ResourcesEventDto(
    val gid: String,
    val resourceGID: String,
    val eventGID: String,
    val requiredQuantity: Int,
    val availableQuantity: Int
)
