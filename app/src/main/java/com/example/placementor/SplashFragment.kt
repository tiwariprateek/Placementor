package com.example.placementor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref=requireContext().getSharedPreferences("Shared_Pref", Context.MODE_PRIVATE)
        val name=sharedPref.getString("name",null)
        val uid=sharedPref.getString("uid",null)
        val enroll=sharedPref.getString("enroll",null)
        val course=sharedPref.getString("course",null)
        val academics=sharedPref.getString("academics",null)
        val imageUri=sharedPref.getString("imageUri",null)
        if (name != null && uid!=null && enroll!=null && course!=null && imageUri!=null && academics != null){
            Log.d("Fragment","MainActivity : Shared values are $uid $name $enroll $course $academics $imageUri")
            Handler().postDelayed({
                context?.let {
                    findNavController().navigate(
                        R.id.action_splashFragment_to_studentDashboardFragment2, null,
                        NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build()
                    )
                }
            }, 1500)
        }
        else{
            Handler().postDelayed({
                context?.let {
                    findNavController().navigate(
                        R.id.action_splashFragment_to_loginFragment, null,
                        NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build()
                    )
                }
            }, 1500)
        }
    }
}
