package com.example.placementor.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.placementor.*
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
        factory= SignUpViewModelFactory(
            UserRepository(FirebaseSource())
        )
        binding=
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_sign_up,container,false)

        val viewModel=ViewModelProvider(this,factory).get(SignUpViewModel::class.java)
        binding.signUpviewmodel=viewModel
        binding.lifecycleOwner = this
//        val action=SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
//        findNavController().navigate(action)
//        binding.textView5.setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.loginFragment,null)
//        )
//        binding.signupButton.setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.academicFragment,null)
//
//        )
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        binding.signupButton.setOnClickListener{
            findNavController().navigate(R.id.academicFragment,null,options)
        }
        binding.textView5.setOnClickListener {
            findNavController().navigate(R.id.loginFragment, null, options)
        }
    }

}
