package com.example.placementor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.placementor.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    var navController:NavController?=null
    private lateinit var binding:FragmentLoginBinding
    private lateinit var viewModel:LoginViewModel
    private lateinit var factory:LoginViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        factory= LoginViewModelFactory(UserRepository(FirebaseSource()))
        binding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        viewModel=ViewModelProvider(this,factory).get(LoginViewModel::class.java)
        binding.viewmodel=viewModel
        binding.setLifecycleOwner(this)
        binding.textView5.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.signUpFragment,null)
        )
        // Inflate the layout for this fragment
        return binding.root

    }


}
