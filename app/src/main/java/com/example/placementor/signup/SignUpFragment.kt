package com.example.placementor.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.placementor.*
import com.example.placementor.databinding.FragmentSignUpBinding
import com.example.placementor.SharedViewModelFactory
import com.example.placementor.SignUpSharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {
    //lateinit var factory: SignUpViewModelFactory
    lateinit var binding:FragmentSignUpBinding
    //lateinit var viewModel:SignUpViewModel
    lateinit var sharedViewModel: SignUpSharedViewModel
    lateinit var sharedViewModelFactory: SharedViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        factory= SignUpViewModelFactory(
//            UserRepository(FirebaseSource())
//        )
        sharedViewModelFactory=
            SharedViewModelFactory(
                UserRepository(FirebaseSource())
            )
        binding=
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_sign_up,container,false)
        //viewModel=ViewModelProvider(requireActivity(),factory).get(SignUpViewModel::class.java)
        sharedViewModel=ViewModelProvider(requireActivity(),sharedViewModelFactory).get(
            SignUpSharedViewModel::class.java)
        binding.signUpviewmodel=sharedViewModel
        binding.lifecycleOwner = this
        sharedViewModel.user.observe(viewLifecycleOwner, Observer {currentuser ->
            if (currentuser==true) {
                Toast.makeText(this.context,"User created successfully",Toast.LENGTH_LONG).show()
                navigatetoacademic()
                Log.d("ViewModel", "Value of user here is $currentuser")
            }
        })


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
    private fun navigatetoacademic() {
        val action=SignUpFragmentDirections
            .actionSignUpFragmentToAcademicFragment()
        NavHostFragment.findNavController(this).navigate(action)
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
            findNavController().navigate(R.id.loginFragment, null, options)
        }
    }
    fun navigate(){
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
    }

}
