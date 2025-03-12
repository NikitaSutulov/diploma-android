package com.nikitasutulov.macsro.repository.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.CreateOperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class OperationTaskStatusRepository {
    private val operationTaskStatusApi = RetrofitClient.operationTaskStatusApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) = operationTaskStatusApi.getAllOperationTaskStatuses(pageNumber, pageSize)

    suspend fun create(createOperationTaskStatusDto: CreateOperationTaskStatusDto) = operationTaskStatusApi.createOperationTaskStatus(createOperationTaskStatusDto)

    suspend fun edit(operationTaskStatusDto: OperationTaskStatusDto) = operationTaskStatusApi.editOperationTaskStatus(operationTaskStatusDto)

    suspend fun getByGID(gid: String) = operationTaskStatusApi.getOperationTaskStatusByGID(gid)

    suspend fun deleteByGID(gid: String) = operationTaskStatusApi.deleteOperationTaskStatusByGID(gid)
}