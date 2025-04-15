package com.nikitasutulov.macsro.viewmodel.factories.volunteer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.VolunteersGroupsRepository
import com.nikitasutulov.macsro.viewmodel.volunteer.VolunteersGroupsViewModel

class VolunteersGroupsViewModelFactory(private val volunteersGroupsRepository: VolunteersGroupsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VolunteersGroupsViewModel::class.java)) {
            return VolunteersGroupsViewModel(volunteersGroupsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
