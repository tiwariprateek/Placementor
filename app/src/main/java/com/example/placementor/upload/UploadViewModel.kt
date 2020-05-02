package com.example.placementor.upload

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.placementor.UserRepository

class UploadViewModel(private val userRepository: UserRepository):ViewModel() {
    var imageUri:Uri?=null
    var enrollnumber:String?=null
    fun uploadImage(){
        userRepository.uploadImage(imageUri!!,enrollnumber!!)
    }
}