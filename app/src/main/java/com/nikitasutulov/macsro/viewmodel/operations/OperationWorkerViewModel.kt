package com.nikitasutulov.macsro.viewmodel.operations

import com.nikitasutulov.macsro.data.dto.operations.operationworker.CreateOperationWorkerDto
import com.nikitasutulov.macsro.data.dto.operations.operationworker.OperationWorkerDto
import com.nikitasutulov.macsro.repository.OperationWorkerRepository
import com.nikitasutulov.macsro.viewmodel.CrudViewModel

class OperationWorkerViewModel(repository: OperationWorkerRepository) :
    CrudViewModel<OperationWorkerDto, CreateOperationWorkerDto>(repository)