package com.nikitasutulov.macsro.data.dto.operations.request

data class RequestsListResultDto(
    val items: List<RequestDto>,
    val totalCount: Int
)
