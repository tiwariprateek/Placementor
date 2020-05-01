package com.example.placementor.education

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.placementor.UserRepository

class EducationViewModelFactory(private val repository: UserRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EducationViewModel(repository) as T
    }
}