package com.nikitasutulov.macsro.data.ui

data class EventResource(
    val gid: String,
    val resourceName: String,
    val measurementUnitName: String,
    val requiredQuantity: Double,
    val availableQuantity: Double
)
