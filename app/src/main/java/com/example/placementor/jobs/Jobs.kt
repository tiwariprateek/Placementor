package com.example.placementor.jobs

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Jobs(
    var Company:String=" ", var Course:String=" ", var LastDate:String=" ",var Profile:String=" ",
    var Salary:String = " " , var Description:String= " ",var About:String=" ",var Eligibility:String=" ",
    var Interview_date:String=" ",var Link:String=" ",var Online_test_date:String=" ",
    var Special_instructions:String=" ", var company_logo:String = "",
var backlogs:Int=1):Parcelable {

}