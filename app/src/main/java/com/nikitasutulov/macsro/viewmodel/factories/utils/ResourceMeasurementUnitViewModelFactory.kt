package com.nikitasutulov.macsro.viewmodel.factories.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.ResourceMeasurementUnitRepository
import com.nikitasutulov.macsro.viewmodel.ResourceMeasurementUnitViewModel

class ResourceMeasurementUnitViewModelFactory(private val resourceMeasurementUnitRepository: ResourceMeasurementUnitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResourceMeasurementUnitViewModel::class.java)) {
            return ResourceMeasurementUnitViewModel(resourceMeasurementUnitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}