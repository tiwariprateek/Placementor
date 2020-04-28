package com.example.placementor.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.placementor.UserRepository

class LoginViewModelFactory(private val repository: UserRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}