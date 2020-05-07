package com.example.placementor.jobs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.placementor.Jobs
import com.example.placementor.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class JobsViewModel():ViewModel() {
    private val fireStore= Firebase.firestore
    private val firebaseAuth=FirebaseAuth.getInstance().currentUser?.uid
    val _Jobs= MutableLiveData<ArrayList<Jobs>>()
    val Jobs: LiveData<ArrayList<Jobs>>
        get()=_Jobs

    init {
        getJobs()
        Log.d("Jobs","INIT")
        Log.d("Jobs","Value of uid is $firebaseAuth")
    }
    fun getJobs(){
        val ref=fireStore.collection("Jobs")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException!=null){
                    Log.d("Jobs","Getting Jobs failed ${firebaseFirestoreException.message}")
                    return@addSnapshotListener
                }
                if(querySnapshot!=null){
                    val allJobs=ArrayList<Jobs>()
                    val documents=querySnapshot.documents
                    documents.forEach{
                        val jobs=it.toObject(com.example.placementor.Jobs::class.java)
                        allJobs.add(jobs!!)
                    }
                    _Jobs.value=allJobs
                }
            }
    }
}