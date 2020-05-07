package com.example.placementor

data class Student(
    var name:String=" "

) {
    override fun toString(): String {
        return "$name "
    }
}