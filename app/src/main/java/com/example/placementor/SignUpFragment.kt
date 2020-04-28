package com.example.placementor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.placementor.databinding.FragmentSignUpBinding

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {
    lateinit var factory: SignUpViewModelFactory
    lateinit var binding:FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        factory= SignUpViewModelFactory(UserRepository(FirebaseSource()))
        binding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up,container,false)

        val viewModel=ViewModelProvider(this,factory).get(SignUpViewModel::class.java)
        binding.signUpviewmodel=viewModel
        binding.setLifecycleOwner(this)
        binding.textView5.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.loginFragment,null)
        )
        // Inflate the layout for this fragment
        return binding.root
    }

}
