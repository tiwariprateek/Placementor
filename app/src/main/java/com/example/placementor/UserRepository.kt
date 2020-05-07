package com.example.placementor

import android.net.Uri

class UserRepository (private val firebaseSource:FirebaseSource){
    fun getUser()=firebaseSource.getUser()
    fun login(email:String,password:String)=firebaseSource.login(email,password)
    fun signUp(email: String,password: String)=firebaseSource.signUp(email,password)
    fun saveUser(name:String,email: String,course:String, enrollnumber:String,backlogs:String, yop:String,
                 graduation:String,xii:String,x:String,imageUri: String)=
        firebaseSource.saveUser(name,email,course,enrollnumber,backlogs,yop,graduation,xii,x,imageUri)
    fun uploadImage(imageUri: Uri)=firebaseSource.uploadImage(imageUri)
    fun uploadCV(documentUri:Uri)=firebaseSource.uploadCV(documentUri)
    fun getUid()=firebaseSource.getUid()
    fun getDocument(userid:String)=firebaseSource.getdocument(userid)
    val imageURL=firebaseSource.imageURL
    val document=firebaseSource.document
    val name=firebaseSource.name
    val enrollnumber=firebaseSource.enroll
    val academics=firebaseSource.academics
    val course=firebaseSource.course
}