package com.nikitasutulov.macsro.viewmodel.operations.operationtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.OperationTaskRepository

class OperationTaskViewModelFactory(private val operationTaskRepository: OperationTaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationTaskViewModel::class.java)) {
            return OperationTaskViewModel(operationTaskRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}