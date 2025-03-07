package com.nikitasutulov.macsro.viewmodel.role

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.RoleRepository

class RoleViewModelFactory(private val roleRepository: RoleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoleViewModel::class.java)) {
            return RoleViewModel(roleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}