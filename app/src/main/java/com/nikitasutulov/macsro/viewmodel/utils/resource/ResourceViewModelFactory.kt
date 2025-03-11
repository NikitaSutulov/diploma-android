package com.nikitasutulov.macsro.viewmodel.utils.resource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.utils.ResourceRepository

class ResourceViewModelFactory(private val resourceRepository: ResourceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResourceViewModel::class.java)) {
            return ResourceViewModel(resourceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}