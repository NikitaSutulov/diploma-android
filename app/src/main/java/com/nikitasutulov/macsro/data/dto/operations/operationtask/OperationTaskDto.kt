package com.nikitasutulov.macsro.data.dto.operations.operationtask

data class OperationTaskDto(
    val gid: String,
    val name: String,
    val taskDescription: String,
    val groupGID: String,
    val taskStatusGID: String
)
