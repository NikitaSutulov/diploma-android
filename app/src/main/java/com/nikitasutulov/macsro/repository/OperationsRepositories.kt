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
import com.nikitasutulov.macsro.data.remote.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Response

class EventRepository : CrudRepository<EventDto, CreateEventDto>(RetrofitClient.eventApi)

class EventStatusRepository :
    CrudRepository<EventStatusDto, CreateEventStatusDto>(RetrofitClient.eventStatusApi)

class EventTypeRepository :
    CrudRepository<EventTypeDto, CreateEventTypeDto>(RetrofitClient.eventTypeApi)

class GroupRepository : CrudRepository<GroupDto, CreateGroupDto>(RetrofitClient.groupApi)

class OperationTaskRepository :
    CrudRepository<OperationTaskDto, CreateOperationTaskDto>(RetrofitClient.operationTaskApi)

class OperationTaskStatusRepository :
    CrudRepository<OperationTaskStatusDto, CreateOperationTaskStatusDto>(RetrofitClient.operationTaskStatusApi)

class OperationWorkerRepository :
    CrudRepository<OperationWorkerDto, CreateOperationWorkerDto>(RetrofitClient.operationWorkerApi)

class ResourcesEventRepository : Repository() {
    val api = RetrofitClient.resourcesEventApi
    suspend fun getAll(
        token: String,
        pageNumber: Int?,
        pageSize: Int?
    ): Response<List<ResourcesEventDto>> = api.getAll(token, pageNumber, pageSize)

    suspend fun create(
        token: String,
        createEntityDto: CreateResourcesEventDto
    ): Response<ResourcesEventDto> = api.create(token, createEntityDto)

    suspend fun edit(token: String, dto: ResourcesEventDto): Response<ResourcesEventDto> =
        api.edit(token, dto)

    suspend fun getByEventGID(
        token: String,
        eventGID: String
    ): Response<List<ResourcesEventDto>> = api.getByEventGID(token, eventGID)

    suspend fun getByResourceGID(
        token: String,
        resourceGID: String
    ): Response<List<ResourcesEventDto>> = api.getByResourceGID(token, resourceGID)

    suspend fun getByGID(token: String, gid: String): Response<ResourcesEventDto> =
        api.getByGID(token, gid)

    suspend fun deleteByGID(token: String, gid: String): Response<ResponseBody> =
        api.deleteByGID(token, gid)
}
