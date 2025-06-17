package com.nikitasutulov.macsro.data.dto.operations.event

data class EventPaginationQueryDto(
    val eventStatusGID: String?,
    val sortByCreateDate: Boolean = true
)
