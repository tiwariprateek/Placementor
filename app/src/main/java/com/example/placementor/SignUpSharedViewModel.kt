package com.example.placementor

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.placementor.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SignUpSharedViewModel(private val userRepository: UserRepository):ViewModel() {
    var name:String?=null
    var enrollnumber:String?=null
    var course:String?=null
    var backlogs:String?=null
    var yop:String?=null
    var imageUri: Uri?=null
    var documentUri: Uri?=null
    var email:String? = null
    var graduation:String?=null
    var xii:String? =null
    var x:String? =null
    var imageuri:String=" "
    var password:String?=null
    private val _uid=MutableLiveData<String>()
    val uid:LiveData<String>
        get() = _uid
    private val _user= MutableLiveData<Boolean>()
    val user: LiveData<Boolean>
        get() = _user
    val _status=userRepository.status
    val status:LiveData<Boolean>
        get() = _status
    val _imageStatus=userRepository.imageStatus
    val imageStatus:LiveData<Boolean>
        get() = _imageStatus
    val _documentStatus=userRepository.imageStatus
    val documentStatus:LiveData<Boolean>
        get() = _documentStatus

    val _exception = MutableLiveData<String>()
    val exception : LiveData<String>
        get() = _exception


    init {
        _uid.value = null
        _user.value = false
        _status.value = false
        _exception.value = null
    }
    fun signUp(){
        userRepository.signUp(email!!,password!!)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    _user.value=true
                }
            }
    }
    fun login(){
//        val loginResult = userRepository.loginWithCoroutines(email!!,password!!)
//        if (loginResult != null){
//            Log.d("Coroutine", "login with coroutines : $loginResult")
//        }
//        else
//            Log.d("Coroutine", "login with coroutines : $loginResult")
        userRepository.login(email!!,password!!)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    _user.value=true
                    _uid.value=userRepository.getUid()
                    _exception.value = null
                }
                else {
                    _user.value = false
                    _uid.value = null
                    _exception.value = task.exception.toString()
                }
            }
    }
    fun saveUserData(){
        userRepository.saveUser(name!!,email!!,course!!,enrollnumber!!,backlogs!!,yop!!,graduation!!,xii!!,x!!,imageuri)
    }
    fun uploadImage(){
        userRepository.uploadImage(imageUri!!)
    }
    fun uploadCV(){
        userRepository.uploadCV(documentUri!!)
    }
}