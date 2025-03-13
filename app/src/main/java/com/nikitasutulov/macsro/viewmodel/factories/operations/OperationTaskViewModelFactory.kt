package com.nikitasutulov.macsro.viewmodel.factories.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.OperationTaskRepository
import com.nikitasutulov.macsro.viewmodel.OperationTaskViewModel

class OperationTaskViewModelFactory(private val operationTaskRepository: OperationTaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationTaskViewModel::class.java)) {
            return OperationTaskViewModel(operationTaskRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}