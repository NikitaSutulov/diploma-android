package com.nikitasutulov.macsro.data.dto.operations.operationtask

data class CreateOperationTaskDto(
    val name: String,
    val taskDescription: String,
    val groupGID: String,
    val taskStatusGID: String
)