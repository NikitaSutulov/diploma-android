package com.nikitasutulov.macsro.viewmodel.factories.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.ResourcesEventRepository
import com.nikitasutulov.macsro.viewmodel.operations.ResourcesEventViewModel

class ResourcesEventViewModelFactory(private val resourcesEventRepository: ResourcesEventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResourcesEventViewModel::class.java)) {
            return ResourcesEventViewModel(resourcesEventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}