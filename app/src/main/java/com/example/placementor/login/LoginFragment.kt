package com.example.placementor.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.placementor.FirebaseSource
import com.example.placementor.R
import com.example.placementor.UserRepository
import com.example.placementor.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    var navController:NavController?=null
    private lateinit var binding:FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var factory: LoginViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        factory= LoginViewModelFactory(
            UserRepository(FirebaseSource())
        )
        binding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_login,container,false)
        viewModel=ViewModelProvider(this,factory).get(LoginViewModel::class.java)
        binding.viewmodel=viewModel
        binding.lifecycleOwner = this
//        binding.textView5.setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.signUpFragment)
//        )
//        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
//        findNavController().navigate(action)
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
        binding.textView5.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment, null, options)
        }
    }
    fun navigate(){
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        findNavController().navigate(action)
    }


}
