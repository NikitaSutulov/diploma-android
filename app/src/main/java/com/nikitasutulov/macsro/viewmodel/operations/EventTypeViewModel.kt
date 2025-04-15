package com.nikitasutulov.macsro.viewmodel.operations

import com.nikitasutulov.macsro.data.dto.operations.eventtype.CreateEventTypeDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
import com.nikitasutulov.macsro.repository.EventTypeRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class EventTypeViewModel(repository: EventTypeRepository) :
    CrudViewModel<EventTypeDto, CreateEventTypeDto>(repository)