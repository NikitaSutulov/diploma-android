package com.nikitasutulov.macsro.viewmodel.factories.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.EventTypeRepository
import com.nikitasutulov.macsro.viewmodel.EventTypeViewModel

class EventTypeViewModelFactory(private val eventTypeRepository: EventTypeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventTypeViewModel::class.java)) {
            return EventTypeViewModel(eventTypeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}