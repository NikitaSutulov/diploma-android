package com.nikitasutulov.macsro.repository.operations

import com.nikitasutulov.macsro.data.dto.operations.operationworker.CreateOperationWorkerDto
import com.nikitasutulov.macsro.data.dto.operations.operationworker.OperationWorkerDto
import com.nikitasutulov.macsro.data.remote.RetrofitClient

class OperationWorkerRepository {
    private val operationWorkerApi = RetrofitClient.operationWorkerApi

    suspend fun getAll(pageNumber: Int?, pageSize: Int?) = operationWorkerApi.getAllOperationWorkers(pageNumber, pageSize)

    suspend fun create(createOperationWorkerDto: CreateOperationWorkerDto) = operationWorkerApi.createOperationWorker(createOperationWorkerDto)

    suspend fun edit(operationWorkerDto: OperationWorkerDto) = operationWorkerApi.editOperationWorker(operationWorkerDto)

    suspend fun getByGID(gid: String) = operationWorkerApi.getOperationWorkerByGID(gid)

    suspend fun deleteByGID(gid: String) = operationWorkerApi.deleteOperationWorkerByGID(gid)
}