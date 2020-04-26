package com.example.placementor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var email:String
    lateinit var password:String
    val firebase = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signup_button.setOnClickListener {
            getInput()
            firebase.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Created new user !!",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Log.d("MainActivity","Message is ${task.exception}")
                        Toast.makeText(this,"Unable to create user !!",Toast.LENGTH_LONG).show()
                    }

                }
        }
        login_button.setOnClickListener {
            getInput()
            firebase.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this,"Login Success !!",Toast.LENGTH_LONG).show()
                }
        }

    }
    fun getInput() {
        email = editText.text.toString()
        password = editText2.text.toString()
    }
}
