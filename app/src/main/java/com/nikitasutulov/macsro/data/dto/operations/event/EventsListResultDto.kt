package com.nikitasutulov.macsro.data.dto.operations.event

data class EventsListResultDto(
    val items: List<EventDto>,
    val totalCount: Int
)
