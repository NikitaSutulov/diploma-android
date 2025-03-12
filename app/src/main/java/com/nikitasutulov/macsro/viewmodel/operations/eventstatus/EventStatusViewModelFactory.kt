package com.nikitasutulov.macsro.viewmodel.operations.eventstatus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.operations.EventStatusRepository

class EventStatusViewModelFactory(private val eventStatusRepository: EventStatusRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventStatusViewModel::class.java)) {
            return EventStatusViewModel(eventStatusRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}