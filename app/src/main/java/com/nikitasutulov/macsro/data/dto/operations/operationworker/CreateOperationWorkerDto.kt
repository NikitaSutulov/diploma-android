package com.nikitasutulov.macsro.data.dto.operations.operationworker

data class CreateOperationWorkerDto(
    val name: String,
    val surname: String,
    val secondName: String,
    val email: String,
    val identificationCode: String,
    val birthDate: String,
    val userGID: String
)
