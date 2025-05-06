package com.nikitasutulov.macsro.data.dto.operations.request

data class RequestDto(
    val gid: String,
    val from: String,
    val to: String,
    val eventGID: String,
    val text: String,
    val isRead: Boolean,
    val createdAt: String
)
