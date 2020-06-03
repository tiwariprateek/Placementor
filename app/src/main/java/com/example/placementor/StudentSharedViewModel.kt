package com.example.placementor

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.placementor.UserRepository
import com.example.placementor.jobs.Jobs
import com.example.placementor.jobs.JobsAdapter
import com.example.placementor.resources.Resources
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StudentSharedViewModel(val repository: UserRepository):ViewModel() {
    private val fireStore= Firebase.firestore
    private val firebaseAuth= FirebaseAuth.getInstance().currentUser?.uid
    val _Resources= MutableLiveData<ArrayList<Resources>>()
    val Resources: LiveData<ArrayList<Resources>>
        get()=_Resources
    val _Jobs= MutableLiveData<ArrayList<Jobs>>()
    val Jobs: LiveData<ArrayList<Jobs>>
        get()=_Jobs
    val uid=repository.getUid()
    val _document=MutableLiveData<DocumentSnapshot>()
    val document:LiveData<DocumentSnapshot>
        get() = _document
    val _name=repository.name
    val name:LiveData<String>
        get() = _name
    val _course=MutableLiveData<String>()
    val course:LiveData<String>
        get() = _course
    val _enroll=MutableLiveData<String>()
    val enroll:LiveData<String>
        get() = _enroll
    val _academics=MutableLiveData<String>()
    val academics:LiveData<String>
        get() = _academics
    val _imageURL=MutableLiveData<String>()
    val imageURL:LiveData<String>
        get() = _imageURL

    val _navigateToSelectedJob=MutableLiveData<Jobs>()
    val navigateToSelectedJob:LiveData<Jobs>
        get() = _navigateToSelectedJob

    val _downloadSelectedResource=MutableLiveData<Resources>()
    val downloadSelectedresource:LiveData<Resources>
        get() = _downloadSelectedResource

    init {
        _document.value=null
        getStudent()
        getResources()
        getJobs()

    }
    fun displayJobsDetails(selectedJob: Jobs){
        _navigateToSelectedJob.value=selectedJob
    }
    fun displayJobDetailsComplete(){
        _navigateToSelectedJob.value=null
    }
    fun downloadResource(selectedResource: Resources){
        _downloadSelectedResource.value=selectedResource
    }
    fun setValues(name:String,enroll:String,course:String,academics:String,imageUri:String){
        _name.value=name
        _enroll.value=enroll
        _course.value=course
        _academics.value=academics
        _imageURL.value=imageUri
    }


    fun getStudent(){
            fireStore.collection("Student Data").document(uid!!).get()
                .addOnSuccessListener {documentSnapshot ->
                    if (documentSnapshot!=null){
                        _document.value=documentSnapshot
                        _name.value=documentSnapshot.getString("name").toString()
                        _enroll.value=documentSnapshot.getString("enrollnumber").toString()
                        _course.value=documentSnapshot.getString("course").toString()
                        _academics.value=documentSnapshot.getString("graduation").toString()
                        _imageURL.value=documentSnapshot.getString("ImageURL").toString()
                        Log.d("Login","Login data is ${documentSnapshot}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Login","Failure to get document $exception")
                }

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
                        val resources=it.toObject(com.example.placementor.resources.Resources::class.java)
                        allResources.add(resources!!)
                    }
                    _Resources.value=allResources
                }
            }
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
                        val jobs=it.toObject(com.example.placementor.jobs.Jobs::class.java)
                        allJobs.add(jobs!!)
                    }
                    _Jobs.value=allJobs
                }
            }
    }
}
@BindingAdapter("imagUrl")
fun loadImage(view: ImageView,imgUrl:String?){
    imgUrl?.let {
        val img=imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(view.context)
            .load(img)
            .into(view)
    }
}
