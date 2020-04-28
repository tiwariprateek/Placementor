package com.example.placementor

class UserRepository (private val firebaseSource:FirebaseSource){
    fun login(email:String,password:String)=firebaseSource.login(email,password)
    fun signUp(email: String,password: String)=firebaseSource.signUp(email,password)
}