package com.nikitasutulov.macsro.viewmodel.factories.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.OperationTaskStatusRepository
import com.nikitasutulov.macsro.viewmodel.OperationTaskStatusViewModel

class OperationTaskStatusViewModelFactory(private val operationTaskStatusRepository: OperationTaskStatusRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationTaskStatusViewModel::class.java)) {
            return OperationTaskStatusViewModel(operationTaskStatusRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}