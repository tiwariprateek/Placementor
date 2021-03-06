package com.example.placementor

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.placementor.studentdashboard.StudentDashboardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment? ?: return
        val navController = host.navController
//        val sharedPref=this.getSharedPreferences("Shared_Pref", Context.MODE_PRIVATE)
//        val name=sharedPref.getString("name",null)
//        val uid=sharedPref.getString("uid",null)
//        val enroll=sharedPref.getString("enroll",null)
//        val course=sharedPref.getString("course",null)
//        val academics=sharedPref.getString("academics",null)
//        val imageUri=sharedPref.getString("imageUri",null)
//        if (name != null && uid!=null && enroll!=null && course!=null && imageUri!=null && academics != null){
//            Log.d("Fragment","MainActivity : Shared values are $uid $name $enroll $course $academics $imageUri")
//            navController.navigate(R.id.studentDashboardFragment)
//        }

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel=NotificationChannel("MyNotification","Mynotifications",NotificationManager.IMPORTANCE_DEFAULT)
            val manager:NotificationManager?=getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
        FirebaseMessaging.getInstance().subscribeToTopic("Job")
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    val msg = "Sucessful"
                }
                Log.d("MainActivity", "Success")
            }

        setupBottomNavMenu(navController)
        //dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.studentDashboardFragment || destination.id == R.id.jobsFragment
                || destination.id==R.id.resourcesFragment || destination.id==R.id.settingsFragment){
                bottomNavigationView.visibility= View.VISIBLE
            }
            else{
                bottomNavigationView.visibility= View.GONE

            }
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navController)
    }
}
