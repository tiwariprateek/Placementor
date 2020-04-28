package com.example.placementor.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.placementor.UserRepository

class SignUpViewModelFactory(private val repository: UserRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpViewModel(repository) as T
    }
}