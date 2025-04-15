package com.nikitasutulov.macsro.viewmodel.factories.volunteer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.VolunteersDistrictsRepository
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteersDistrictsViewModel

class VolunteersDistrictsViewModelFactory(private val volunteersDistrictsRepository: VolunteersDistrictsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VolunteersDistrictsViewModel::class.java)) {
            return VolunteersDistrictsViewModel(volunteersDistrictsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
