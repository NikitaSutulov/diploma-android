package com.nikitasutulov.macsro.viewmodel.operations.operationtaskstatus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.operations.OperationTaskStatusRepository

class OperationTaskStatusViewModelFactory(private val operationTaskStatusRepository: OperationTaskStatusRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationTaskStatusViewModel::class.java)) {
            return OperationTaskStatusViewModel(operationTaskStatusRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}