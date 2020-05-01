package com.example.placementor

class UserRepository (private val firebaseSource:FirebaseSource){
    fun getUser()=firebaseSource.getUser()
    fun login(email:String,password:String)=firebaseSource.login(email,password)
    fun signUp(email: String,password: String)=firebaseSource.signUp(email,password)
    fun saveUser(name:String, enrollnumber:String, course:String, backlogs:String, yop:String)=
        firebaseSource.saveUser(name,enrollnumber,course,backlogs,yop)
}