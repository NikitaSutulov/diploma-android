package com.nikitasutulov.macsro.viewmodel.operations

import com.nikitasutulov.macsro.data.dto.operations.eventstatus.CreateEventStatusDto
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
import com.nikitasutulov.macsro.repository.EventStatusRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class EventStatusViewModel(repository: EventStatusRepository) :
    CrudViewModel<EventStatusDto, CreateEventStatusDto>(repository)