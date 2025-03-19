package com.nikitasutulov.macsro.repository

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
import com.nikitasutulov.macsro.data.remote.api.operations.EventApi
import com.nikitasutulov.macsro.data.remote.api.operations.EventStatusApi
import com.nikitasutulov.macsro.data.remote.api.operations.EventTypeApi
import com.nikitasutulov.macsro.data.remote.api.operations.GroupApi
import com.nikitasutulov.macsro.data.remote.api.operations.OperationTaskApi
import com.nikitasutulov.macsro.data.remote.api.operations.OperationTaskStatusApi
import com.nikitasutulov.macsro.data.remote.api.operations.OperationWorkerApi
import com.nikitasutulov.macsro.data.remote.api.operations.ResourcesEventApi

class EventRepository(api: EventApi): CrudRepository<EventDto, CreateEventDto>(api)

class EventStatusRepository(api: EventStatusApi): CrudRepository<EventStatusDto, CreateEventStatusDto>(api)

class EventTypeRepository(api: EventTypeApi): CrudRepository<EventTypeDto, CreateEventTypeDto>(api)

class GroupRepository(api: GroupApi): CrudRepository<GroupDto, CreateGroupDto>(api)

class OperationTaskRepository(api: OperationTaskApi): CrudRepository<OperationTaskDto, CreateOperationTaskDto>(api)

class OperationTaskStatusRepository(api: OperationTaskStatusApi): CrudRepository<OperationTaskStatusDto, CreateOperationTaskStatusDto>(api)

class OperationWorkerRepository(api: OperationWorkerApi): CrudRepository<OperationWorkerDto, CreateOperationWorkerDto>(api)

class ResourcesEventRepository(api: ResourcesEventApi): CrudRepository<ResourcesEventDto, CreateResourcesEventDto>(api)
