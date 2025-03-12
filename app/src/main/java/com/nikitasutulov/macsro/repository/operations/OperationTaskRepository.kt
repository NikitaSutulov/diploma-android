package com.nikitasutulov.macsro.repository.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtask.CreateOperationTaskDto
import com.nikitasutulov.macsro.data.dto.operations.operationtask.OperationTaskDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class OperationTaskRepository {
    private val operationTaskApi = RetrofitClient.operationTaskApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) = operationTaskApi.getAllOperationTasks(pageNumber, pageSize)

    suspend fun create(createOperationTaskDto: CreateOperationTaskDto) = operationTaskApi.createOperationTask(createOperationTaskDto)

    suspend fun edit(operationTaskDto: OperationTaskDto) = operationTaskApi.editOperationTask(operationTaskDto)

    suspend fun getByGID(gid: String) = operationTaskApi.getOperationTaskByGID(gid)

    suspend fun deleteByGID(gid: String) = operationTaskApi.deleteOperationTaskByGID(gid)
}