package com.example.placementor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.placementor.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private lateinit var viewModel:LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        viewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewmodel=viewModel
        binding.setLifecycleOwner(this)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

}
