package com.nikitasutulov.macsro.viewmodel.operations

import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.CreateOperationTaskStatusDto
import com.nikitasutulov.macsro.data.dto.operations.operationtaskstatus.OperationTaskStatusDto
import com.nikitasutulov.macsro.repository.OperationTaskStatusRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class OperationTaskStatusViewModel(repository: OperationTaskStatusRepository) :
    CrudViewModel<OperationTaskStatusDto, CreateOperationTaskStatusDto>(repository)