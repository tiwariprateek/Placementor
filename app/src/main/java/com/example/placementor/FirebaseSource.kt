package com.example.placementor


import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.net.URL

class FirebaseSource {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val fireStore=Firebase.firestore
    //private val firebaseAuth= FirebaseAuth.getInstance()
    private val firebaseStorage=Firebase.storage
    private val refrence=firebaseStorage.reference
    private val uid=firebaseAuth.currentUser?.uid
    val document=MutableLiveData<DocumentSnapshot>()
    val name=MutableLiveData<String>()
    val enroll=MutableLiveData<String>()
    val course=MutableLiveData<String>()
    val academics=MutableLiveData<String>()
    val imageURL=MutableLiveData<String>()





    fun login(email:String,password:String) =
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("ViewModel","Success !!")
                }
                else{
                    Log.d("ViewModel","Failure !!${task.exception}")
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
    fun getUid()=firebaseAuth.currentUser?.uid

    fun saveUser(name:String,email: String, course:String, enrollnumber:String,
                 backlogs:String, yop:String, graduation:String,xii:String,x:String,imageUri: String) {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "course" to course,
            "enrollnumber" to enrollnumber,
            "backlogs" to backlogs,
            "yop" to yop,
            "graduation" to graduation,
            "XII" to xii,
            "X" to x,
            "ImageURL" to imageUri
        )
        fireStore.collection("Student Data").document(uid!!).set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "DocumentSnapshot added")
            }
            .addOnFailureListener {
                Log.d("Firestore", "Error in document addition $it")
            }
    }
    fun uploadImage(imageUri: Uri) {
        var imageUrl: String?
        val imageReference=refrence.child("images/$uid")
        imageReference.putFile(imageUri)
            .addOnSuccessListener{upload ->
                    imageReference.downloadUrl.addOnSuccessListener {
                        imageUrl=it.toString()
                        fireStore.collection("Student Data").document(uid!!).update("ImageURL",imageUrl)
                    }
                    Log.d("Upload", "Upload successful")
            }
    }
    fun uploadCV(documentUri:Uri){
        val documentReference=refrence.child("Resumes/$uid")
        documentReference.putFile(documentUri)
            .addOnCompleteListener { upload ->
                if (upload.isComplete)
                    Log.d("Upload","Upload sucessfull")
                else
                    Log.d("Upload","Upload Failed due to ${upload.exception}")

            }
    }

    fun getdocument(userid:String)=
        fireStore.collection("Student Data").document(userid).get()
            .addOnSuccessListener {documentSnapshot ->
                if (documentSnapshot!=null){
                    imageURL.value=documentSnapshot.getString("ImageURL").toString()
                    document.value=documentSnapshot
                    name.value=documentSnapshot.getString("name").toString()
                    enroll.value=documentSnapshot.getString("enrollnumber").toString()
                    course.value=documentSnapshot.getString("course").toString()
                    academics.value=documentSnapshot.getString("graduation").toString()
                    Log.d("Login","Login data is ${documentSnapshot}")
                }

            }
            .addOnFailureListener { exception ->
                Log.d("Login","Failure to get document $exception")
            }



    




}