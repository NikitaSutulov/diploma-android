package com.nikitasutulov.macsro.viewmodel.factories.volunteer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.VolunteerRepository
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteerViewModel

class VolunteerViewModelFactory(private val volunteerRepository: VolunteerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VolunteerViewModel::class.java)) {
            return VolunteerViewModel(volunteerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
