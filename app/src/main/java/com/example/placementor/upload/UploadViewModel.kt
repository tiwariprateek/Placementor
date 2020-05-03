package com.example.placementor.upload

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.placementor.UserRepository

class UploadViewModel(private val userRepository: UserRepository,
                        private val application: Application):ViewModel() {
    var imageUri:Uri?=null
    var enrollnumber:String?=null
    var documentUri:Uri?=null
    fun uploadImage(){
        userRepository.uploadImage(imageUri!!,enrollnumber!!)
    }
    fun uploadCV(){
        userRepository.uploadCV(documentUri!!,enrollnumber!!)
    }

}