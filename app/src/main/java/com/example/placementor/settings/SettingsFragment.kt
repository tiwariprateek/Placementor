package com.example.placementor.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.placementor.FirebaseSource

import com.example.placementor.R
import com.example.placementor.UserRepository
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_out.setOnClickListener {
            val sharedPref=requireContext().getSharedPreferences("Shared_Pref",Context.MODE_PRIVATE)
            with(sharedPref.edit()){
                clear()
                    .apply()
            }
            UserRepository(FirebaseSource()).signout()
            Log.d("SharedPref","Value of shared pref is $sharedPref")
            activity?.finish()
        }
    }

}
