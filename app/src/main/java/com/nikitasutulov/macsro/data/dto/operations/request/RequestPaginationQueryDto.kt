package com.nikitasutulov.macsro.data.dto.operations.request

data class RequestPaginationQueryDto(
    val pageNumber: Int? = null,
    val pageSize: Int? = null,
    val isRead: Boolean,
    val from: String,
    val to: String,
    val eventGID: String
)
