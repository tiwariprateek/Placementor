package com.example.placementor

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseSource {
    private val fireStore=Firebase.firestore
    private val firebaseAuth= FirebaseAuth.getInstance()



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

    fun saveUser(name:String, enrollnumber:String, course:String, backlogs:String, yop:String) {
        val user = hashMapOf(
            "name" to name,
            "enrollnumber" to enrollnumber,
            "course" to course,
            "backlogs" to backlogs,
            "yop" to yop
        )
        fireStore.collection("Student Data").document(enrollnumber).set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "DocumentSnapshot added")
            }
            .addOnFailureListener {
                Log.d("Firestore", "Error in document addition $it")
            }
    }

}