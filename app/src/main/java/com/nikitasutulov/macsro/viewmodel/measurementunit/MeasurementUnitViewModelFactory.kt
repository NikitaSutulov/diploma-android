package com.nikitasutulov.macsro.viewmodel.measurementunit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.DistrictRepository
import com.nikitasutulov.macsro.repository.MeasurementUnitRepository
import com.nikitasutulov.macsro.viewmodel.measurementunit.MeasurementUnitViewModel

class MeasurementUnitViewModelFactory(private val measurementUnitRepository: MeasurementUnitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeasurementUnitViewModel::class.java)) {
            return MeasurementUnitViewModel(measurementUnitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}