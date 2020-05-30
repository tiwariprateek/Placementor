package com.example.placementor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudentSharedViewModelFactory(private val userRepository: UserRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StudentSharedViewModel(userRepository) as T
    }
}