package com.example.placementor.upload

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.placementor.UserRepository

class UploadViewModel(private val userRepository: UserRepository):ViewModel() {
    fun selectImage(){
        userRepository.selectImage()
    }
}