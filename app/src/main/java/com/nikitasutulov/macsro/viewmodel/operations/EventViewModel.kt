package com.nikitasutulov.macsro.viewmodel.operations

import com.nikitasutulov.macsro.data.dto.operations.event.CreateEventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
import com.nikitasutulov.macsro.repository.EventRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class EventViewModel(repository: EventRepository) :
    CrudViewModel<EventDto, CreateEventDto>(repository)