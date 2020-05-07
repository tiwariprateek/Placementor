package com.example.placementor.resources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.placementor.Resources
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ResourceViewModel(): ViewModel() {
    private val fireStore= Firebase.firestore
    private val firebaseAuth= FirebaseAuth.getInstance().currentUser?.uid
    val _Resources= MutableLiveData<ArrayList<Resources>>()
    val Resources: LiveData<ArrayList<Resources>>
        get()=_Resources

    init {
        getResources()
        Log.d("Resources","INIT")
        Log.d("Resources","Value of uid is $firebaseAuth")
    }
    fun getResources(){
        val ref=fireStore.collection("Resources")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException!=null){
                    Log.d("Jobs","Getting Jobs failed ${firebaseFirestoreException.message}")
                    return@addSnapshotListener
                }
                if(querySnapshot!=null){
                    val allResources=ArrayList<Resources>()
                    val documents=querySnapshot.documents
                    documents.forEach{
                        val resources=it.toObject(com.example.placementor.Resources::class.java)
                        allResources.add(resources!!)
                    }
                    _Resources.value=allResources
                }
            }
    }
}