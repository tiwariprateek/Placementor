package com.example.placementor.signup

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    lateinit var binding:FragmentSignUpBinding
    lateinit var sharedViewModel: SignUpSharedViewModel
    lateinit var sharedViewModelFactory: SharedViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModelFactory=
            SharedViewModelFactory(
                UserRepository(FirebaseSource())
            )
        binding=
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_sign_up,container,false)
        sharedViewModel=ViewModelProvider(requireActivity(),sharedViewModelFactory).get(
            SignUpSharedViewModel::class.java)
        binding.signUpviewmodel=sharedViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog=LoadingDialog(requireActivity()).buildDialog(requireContext(),requireActivity())
        val email = binding.signupEmail.text
        val password = binding.signupPassword.text
        binding.signupButton.setOnClickListener {
            if(email!!.isEmpty() || password!!.isEmpty()){
                if (email.isEmpty())
                    binding.signupEmail.error = "Email cannot be left empty"
                if (password!!.isEmpty())
                    binding.signupPassword.error = "Password cannot be left empty"
            }
            else {
                dialog.show()
                sharedViewModel.signUp()
            }
        }
        sharedViewModel.user.observe(viewLifecycleOwner, Observer {currentuser ->
            if (currentuser==true) {
                Toast.makeText(this.context,"User created successfully",Toast.LENGTH_LONG).show()
                dialog.dismiss()
                navigatetoacademic()
                Log.d("ViewModel", "Value of user here is $currentuser")
            }
        })

        binding.textView5.setOnClickListener {
            navigateToLogin()
        }
    }
    fun navigateToLogin(){
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        findNavController().navigate(R.id.loginFragment, null, options)
    }
    private fun navigatetoacademic() {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        findNavController().navigate(R.id.academicFragment, null, options)
    }
}
