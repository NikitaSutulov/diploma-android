package com.nikitasutulov.macsro.viewmodel.operations.resourcesevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.ResourcesEventRepository

class ResourcesEventViewModelFactory(private val resourcesEventRepository: ResourcesEventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResourcesEventViewModel::class.java)) {
            return ResourcesEventViewModel(resourcesEventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}