package com.nikitasutulov.macsro.data.dto.operations.request

data class CreateRequestDto(
    val from: String,
    val to: String,
    val eventGID: String,
    val text: String,
    val isRead: Boolean
)
