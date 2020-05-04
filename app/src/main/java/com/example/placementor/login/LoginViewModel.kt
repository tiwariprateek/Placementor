package com.example.placementor.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.placementor.UserRepository


class LoginViewModel(private val repository: UserRepository):ViewModel() {

    var email:String?=null
    var password:String?=null
    private val _uid=MutableLiveData<String>()
    val uid:LiveData<String>
        get() = _uid
    private val _user= MutableLiveData<Boolean>()
    val user: LiveData<Boolean>
        get() = _user

    init {
        _user.value=false
        _uid.value=null
    }
    fun login(){
        repository.login(email!!,password!!)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    _user.value=true
                    _uid.value=repository.getUid()
                }
            }
    }


//    fun firebaseLogin(){
//        firebase.signInWithEmailAndPassword(email!!,password!!)
//            .addOnCompleteListener {task ->
//                if(task.isSuccessful){
//                    Log.d("ViewModel","com.example.placementor.Login Sucess")
//                }
//                else{
//                    Log.d("ViewModel","The error message is ${task.exception}")
//                }
//
//            }
//
//    }
}