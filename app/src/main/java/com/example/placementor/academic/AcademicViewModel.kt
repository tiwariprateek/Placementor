package com.example.placementor.academic

import androidx.lifecycle.ViewModel
import com.example.placementor.UserRepository

class AcademicViewModel(private val repository: UserRepository):ViewModel() {
    var name:String?=null
    var enrollnumber:String?=null
    var course:String?=null
    var backlogs:String?=null
    var yop:String?=null



}