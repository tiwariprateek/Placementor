package com.example.placementor.studentdashboard

import android.R
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.placementor.Student
import com.example.placementor.UserRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class DashboardViewModel(private val repository: UserRepository):ViewModel() {
    private val fireStore= Firebase.firestore
    val _Student= MutableLiveData<ArrayList<Student>>()
    val Student: LiveData<ArrayList<Student>>
        get()=_Student




    val uid=repository.getUid()
    val document:LiveData<DocumentSnapshot>
        get() = repository.document
    val _name=repository.name
    val name:LiveData<String>
        get() = _name
    val course:LiveData<String>
        get() = repository.course
    val enroll:LiveData<String>
        get() = repository.enrollnumber
    val academics:LiveData<String>
        get() = repository.academics
    var _imageURL=repository.imageURL
    val imageURL:LiveData<String>
        get() = _imageURL

    init {
        getStudent()
        _imageURL.value=null
        repository.getDocument(uid!!)
        Log.d("Login","INIT")
    }
//    fun getData(){
//        val document=repository.getDocument(uid!!)
//    }
    fun setImage(imageView: ImageView){
        Picasso.get().load(imageURL.value).into(imageView)
}


    fun getname():String{
        var name=document.value
        Log.d("Login","Name with document snapshot ${name?.getString("name")}")
        return name?.getString("name").toString()
    }
    fun getStudent(){
        val ref=fireStore.collection("Student Data")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException!=null){
                    Log.d("Jobs","Getting Jobs failed ${firebaseFirestoreException.message}")
                    return@addSnapshotListener
                }
                if(querySnapshot!=null){
                    val allStudent=ArrayList<Student>()
                    val documents=querySnapshot.documents
                    documents.forEach{
                        val students=it.toObject(com.example.placementor.Student::class.java)
                        allStudent.add(students!!)
                    }
                    _Student.value=allStudent
                }
            }
    }

}
//@BindingAdapter("imageUrl")
//fun loadImage(view: ImageView, imageUrl: String?){
//    Picasso.get()
//        .load(imageUrl)
//        .into(view)
//    Log.d("Login","Loading image")
//}