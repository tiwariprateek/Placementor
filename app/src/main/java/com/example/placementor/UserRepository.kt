package com.example.placementor

import android.net.Uri

class UserRepository (private val firebaseSource:FirebaseSource){
    fun getUser()=firebaseSource.getUser()
    fun login(email:String,password:String)=firebaseSource.login(email,password)
    fun signUp(email: String,password: String)=firebaseSource.signUp(email,password)
    fun saveUser(name:String,email: String,course:String, enrollnumber:String,backlogs:String, yop:String,
                 graduation:String,xii:String,x:String)=
        firebaseSource.saveUser(name,email,course,enrollnumber,backlogs,yop,graduation,xii,x)
    fun uploadImage(imageUri: Uri)=firebaseSource.uploadImage(imageUri)
    fun uploadCV(documentUri:Uri)=firebaseSource.uploadCV(documentUri)
    fun getUid()=firebaseSource.getUid()
}