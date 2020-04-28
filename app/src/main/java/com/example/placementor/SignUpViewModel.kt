package com.example.placementor

import androidx.lifecycle.ViewModel

class SignUpViewModel(private val repository: UserRepository):ViewModel() {
    var email:String?=null
    var password:String?=null

    fun signUp(){
        repository.signUp(email!!,password!!)
    }
}