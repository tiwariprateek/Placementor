package com.example.placementor.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.placementor.UserRepository

class SignUpViewModel(private val repository: UserRepository):ViewModel() {
    var email:String?=null
    var password:String?=null
    private val _user=MutableLiveData<Boolean>()
    val user:LiveData<Boolean>
    get() = _user

init {
    _user.value=false
}


    fun signUp(){
        repository.signUp(email!!,password!!)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    _user.value=true
                }
            }

    }
//    fun getUser(){
//        repository.getUser()
//    }
}