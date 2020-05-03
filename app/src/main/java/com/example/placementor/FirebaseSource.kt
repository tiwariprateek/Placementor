package com.example.placementor


import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

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
        val imageReference=refrence.child("images/$enrollnumber")
        imageReference.putFile(imageUri)
            .addOnCompleteListener{upload ->
                if (upload.isComplete)
                    Log.d("Upload","Upload successful")
                else
                    Log.d("Upload","Upload Failure ${upload.exception}")

            }
    }
    fun uploadCV(documentUri:Uri,enrollnumber: String){
        val documentReference=refrence.child("Resumes/$enrollnumber")
        documentReference.putFile(documentUri)
            .addOnCompleteListener { upload ->
                if (upload.isComplete)
                    Log.d("Upload","Upload sucessfull")
                else
                    Log.d("Upload","Upload Failed due to ${upload.exception}")

            }
    }



}