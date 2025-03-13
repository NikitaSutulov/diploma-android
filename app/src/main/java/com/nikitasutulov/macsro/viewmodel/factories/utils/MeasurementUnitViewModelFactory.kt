package com.nikitasutulov.macsro.viewmodel.factories.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.MeasurementUnitRepository
import com.nikitasutulov.macsro.viewmodel.MeasurementUnitViewModel

class MeasurementUnitViewModelFactory(private val measurementUnitRepository: MeasurementUnitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeasurementUnitViewModel::class.java)) {
            return MeasurementUnitViewModel(measurementUnitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}