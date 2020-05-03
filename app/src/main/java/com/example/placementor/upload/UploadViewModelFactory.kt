package com.example.placementor.upload

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.placementor.UserRepository

class UploadViewModelFactory(private val userRepository: UserRepository,
                            private val application: Application):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UploadViewModel(userRepository,application) as T
    }
}