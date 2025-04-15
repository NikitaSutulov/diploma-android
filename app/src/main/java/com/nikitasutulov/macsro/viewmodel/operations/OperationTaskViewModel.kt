package com.nikitasutulov.macsro.viewmodel.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtask.CreateOperationTaskDto
import com.nikitasutulov.macsro.data.dto.operations.operationtask.OperationTaskDto
import com.nikitasutulov.macsro.repository.OperationTaskRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class OperationTaskViewModel(repository: OperationTaskRepository) :
    CrudViewModel<OperationTaskDto, CreateOperationTaskDto>(repository)