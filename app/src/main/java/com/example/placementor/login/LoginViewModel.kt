package com.example.placementor.login

import androidx.lifecycle.ViewModel
import com.example.placementor.UserRepository


class LoginViewModel(private val repository: UserRepository):ViewModel() {

    var email:String?=null
    var password:String?=null

    fun login(){
        repository.login(email!!,password!!)
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