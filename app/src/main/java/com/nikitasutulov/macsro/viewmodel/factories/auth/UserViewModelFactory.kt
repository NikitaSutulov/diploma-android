package com.nikitasutulov.macsro.viewmodel.factories.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitasutulov.macsro.repository.UserRepository
import com.nikitasutulov.macsro.viewmodel.auth.UserViewModel

class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}