package com.example.placementor

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class LoginViewModel:ViewModel() {
    val firebase=FirebaseAuth.getInstance()
    var email:String?=null
    var password:String?=null

    fun firebaseLogin(){
        firebase.signInWithEmailAndPassword(email!!,password!!)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    Log.d("ViewModel","Login Sucess")
                }
                else{
                    Log.d("ViewModel","The error message is ${task.exception}")
                }

            }

    }
}