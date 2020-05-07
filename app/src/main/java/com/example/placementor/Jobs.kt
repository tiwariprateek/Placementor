package com.example.placementor

data class Jobs(var Company:String=" ", var Course:String=" ", var LastDate:String=" ",var Profile:String=" ",
var Salary:String = " ") {
    override fun toString(): String {
        return "$Company $Course $LastDate $Profile $Salary"
    }
}