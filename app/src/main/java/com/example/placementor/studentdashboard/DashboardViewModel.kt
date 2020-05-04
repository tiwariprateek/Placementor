package com.example.placementor.studentdashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.placementor.UserRepository
import com.google.firebase.firestore.DocumentSnapshot

class DashboardViewModel(private val repository: UserRepository):ViewModel() {
    val uid=repository.getUid()
    val document:LiveData<DocumentSnapshot>
        get() = repository.document
    val name:LiveData<String>
        get() = repository.name
    val course:LiveData<String>
        get() = repository.course
    val enroll:LiveData<String>
        get() = repository.enrollnumber
    val academics:LiveData<String>
        get() = repository.academics

    init {
     getData()
    }
    fun getData(){
        repository.getDocument(uid!!)
        Log.d("Login","Name is ${name.value}")
    }
    fun getname():String{
        var name=document.value
        Log.d("Login","Name with document snapshot ${name?.getString("name")}")
        return name?.getString("name").toString()
    }

}