package com.nikitasutulov.macsro.data.ui

import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
import com.nikitasutulov.macsro.data.dto.utils.district.DistrictDto
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val gid: String,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val eventType: EventTypeDto,
    val eventStatus: EventStatusDto,
    val district: DistrictDto,
    val coordinatorGID: String,
    val dispatcherGID: String
) : java.io.Serializable
