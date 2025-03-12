package com.nikitasutulov.macsro.viewmodel.operations.operationworker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.operations.OperationWorkerRepository

class OperationWorkerViewModelFactory(private val operationWorkerRepository: OperationWorkerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationWorkerViewModel::class.java)) {
            return OperationWorkerViewModel(operationWorkerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}