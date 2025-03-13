package com.nikitasutulov.macsro.viewmodel.factories.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.GroupRepository
import com.nikitasutulov.macsro.viewmodel.GroupViewModel

class GroupViewModelFactory(private val groupRepository: GroupRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroupViewModel::class.java)) {
            return GroupViewModel(groupRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}