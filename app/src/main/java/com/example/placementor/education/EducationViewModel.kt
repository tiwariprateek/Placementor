package com.example.placementor.education

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.placementor.UserRepository

class EducationViewModel(private val userRepository: UserRepository):ViewModel() {
    var name:String? = null
    var email:String? = null
    var course:String? =null
    var enroll:String? =null
    var backlogs:String? =null
    var yop:String? =null
    var graduation:String?=null
    var xii:String? =null
    var x:String? =null
    var imageUri:String=" "

    fun saveUserData(){
        userRepository.saveUser(name!!,email!!,course!!,enroll!!,backlogs!!,yop!!,graduation!!,xii!!,x!!,imageUri)
    }


}