package com.nikitasutulov.macsro.viewmodel

import com.nikitasutulov.macsro.data.dto.operations.event.CreateEventDto
import com.nikitasutulov.macsro.data.dto.operations.event.EventDto
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.CreateEventStatusDto
import com.nikitasutulov.macsro.data.dto.operations.eventstatus.EventStatusDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.CreateEventTypeDto
import com.nikitasutulov.macsro.data.dto.operations.eventtype.EventTypeDto
import com.nikitasutulov.macsro.data.dto.operations.group.CreateGroupDto
import com.nikitasutulov.macsro.data.dto.operations.group.GroupDto
import com.nikitasutulov.macsro.data.dto.operations.operationtask.CreateOperationTaskDto
import com.nikitasutulov.macsro.data.dto.operations.operationtask.OperationTaskDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.CreateOperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.operationworker.CreateOperationWorkerDto
import com.nikitasutulov.macsro.data.dto.operations.operationworker.OperationWorkerDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.CreateResourcesEventDto
import com.nikitasutulov.macsro.data.dto.operations.resourcesevent.ResourcesEventDto
import com.nikitasutulov.macsro.repository.EventRepository
import com.nikitasutulov.macsro.repository.EventStatusRepository
import com.nikitasutulov.macsro.repository.EventTypeRepository
import com.nikitasutulov.macsro.repository.GroupRepository
import com.nikitasutulov.macsro.repository.OperationTaskRepository
import com.nikitasutulov.macsro.repository.OperationTaskStatusRepository
import com.nikitasutulov.macsro.repository.OperationWorkerRepository
import com.nikitasutulov.macsro.repository.ResourcesEventRepository

class EventViewModel(repository: EventRepository) :
    CrudViewModel<EventDto, CreateEventDto>(repository)

class EventStatusViewModel(repository: EventStatusRepository) :
    CrudViewModel<EventStatusDto, CreateEventStatusDto>(repository)

class EventTypeViewModel(repository: EventTypeRepository) :
    CrudViewModel<EventTypeDto, CreateEventTypeDto>(repository)

class GroupViewModel(repository: GroupRepository) :
    CrudViewModel<GroupDto, CreateGroupDto>(repository)

class OperationTaskViewModel(repository: OperationTaskRepository) :
    CrudViewModel<OperationTaskDto, CreateOperationTaskDto>(repository)

class OperationTaskStatusViewModel(repository: OperationTaskStatusRepository) :
    CrudViewModel<OperationTaskStatusDto, CreateOperationTaskStatusDto>(repository)

class OperationWorkerViewModel(repository: OperationWorkerRepository) :
    CrudViewModel<OperationWorkerDto, CreateOperationWorkerDto>(repository)

class ResourcesEventViewModel(repository: ResourcesEventRepository) :
    CrudViewModel<ResourcesEventDto, CreateResourcesEventDto>(repository)
