package com.example.placementor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class FirebaseSource {
    private val fireStore=Firebase.firestore
    private val firebaseAuth= FirebaseAuth.getInstance()
    private val firebaseStorage=Firebase.storage
    private val refrence=firebaseStorage.reference



    fun login(email:String,password:String) =
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("ViewModel","Success !!")
                }
                else{
                    Log.d("ViewModel","Failure !!")
                }
            }

    fun signUp(email: String,password:String)=
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Log.d("ViewModel","User Created successfully")
                }
                else{
                    Log.d("ViewModel","Failure !! ${task.exception}")
                }
            }

    fun getUser()=firebaseAuth.currentUser

    fun saveUser(name:String,email: String, course:String, enrollnumber:String,
                 backlogs:String, yop:String, graduation:String,xii:String,x:String) {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "course" to course,
            "enrollnumber" to enrollnumber,
            "backlogs" to backlogs,
            "yop" to yop,
            "graduation" to graduation,
            "XII" to xii,
            "X" to x
        )
        fireStore.collection("Student Data").document(enrollnumber).set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "DocumentSnapshot added")
            }
            .addOnFailureListener {
                Log.d("Firestore", "Error in document addition $it")
            }
    }
    fun uploadImage(imageUri: Uri, enrollnumber: String) {
        val imageRefrence=refrence.child("images/$enrollnumber")
        imageRefrence.putFile(imageUri)
            .addOnCompleteListener{upload ->
                if (upload.isComplete)
                    Log.d("Upload","Upload successful")
                else
                    Log.d("Upload","Upload Failure ${upload.exception}")

            }
    }
//    fun selectImage(){
//        val intent=Intent()
//        intent.setType("image/*")
//        intent.setAction(Intent.ACTION_GET_CONTENT)
//        Activity().startActivityForResult(intent,0)
//    }


}