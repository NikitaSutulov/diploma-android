package com.nikitasutulov.macsro.viewmodel.utils.resourcemeasurementunit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.ResourceMeasurementUnitRepository

class ResourceMeasurementUnitViewModelFactory(private val resourceRepository: ResourceMeasurementUnitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResourceMeasurementUnitViewModel::class.java)) {
            return ResourceMeasurementUnitViewModel(resourceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}