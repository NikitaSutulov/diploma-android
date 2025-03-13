package com.nikitasutulov.macsro.viewmodel.utils.district

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.DistrictRepository

class DistrictViewModelFactory(private val districtRepository: DistrictRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DistrictViewModel::class.java)) {
            return DistrictViewModel(districtRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}