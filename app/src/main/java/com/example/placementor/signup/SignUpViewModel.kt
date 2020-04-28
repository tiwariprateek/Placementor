package com.example.placementor.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.placementor.UserRepository

class SignUpViewModel(private val repository: UserRepository):ViewModel() {
    var email:String?=null
    var password:String?=null

    fun signUp(){
        repository.signUp(email!!,password!!)
    }
}