package com.example.placementor.login

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.placementor.*
import com.example.placementor.databinding.FragmentLoginBinding
import com.example.placementor.SharedViewModelFactory
import com.example.placementor.SignUpSharedViewModel
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private lateinit var sharedViewModel: SignUpSharedViewModel
    private lateinit var sharedFactory: SharedViewModelFactory
    val MY_PERMISSIONS_REQUEST_INTERNET=1001
    var permission=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedFactory=
            SharedViewModelFactory(
                UserRepository(FirebaseSource())
            )
        binding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_login,container,false)
        sharedViewModel=ViewModelProvider(this,sharedFactory).get(SignUpSharedViewModel::class.java)
        binding.viewmodel=sharedViewModel
        binding.lifecycleOwner = this
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.INTERNET
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.INTERNET),
                MY_PERMISSIONS_REQUEST_INTERNET
            )

        } else {
            permission=true
            Log.d("Upload Fragment", "Permission granted")
        }

        return binding.root


    }

    private fun saveUid(uid: String) {
        val sharedPref = requireContext().getSharedPreferences("Shared_Pref", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("uid", uid)
            commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadingDialog=LoadingDialog(requireActivity())
        Log.d("Login Fragment","Permission is $permission")
        val dialog=loadingDialog.buildDialog(requireContext(),requireActivity())
        signup_button.setOnClickListener {
            dialog.show()
            sharedViewModel.login()
        }
        sharedViewModel.user.observe(viewLifecycleOwner, Observer { user->
            if (user)
                sharedViewModel.uid.observe(viewLifecycleOwner, Observer { uid ->
                    if (uid != null) {
                        saveUid(uid)
                        dialog.dismiss()
                        navigate()
                    }
                })
        })
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
        val action = LoginFragmentDirections.actionLoginFragmentToStudentDashboardFragment()
        findNavController().navigate(action)
    }
    fun saveDataLocally(name:String?,uid:String?,enroll:String?,course:String?,academics:String?,imageUri:String?){
        val sharedPref=requireContext().getSharedPreferences("Shared_Pref",Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("name",name)
            putString("uid", uid)
            putString("enroll",enroll)
            putString("course",course)
            putString("academics",academics)
            putString("imagUri",imageUri)
            commit()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_INTERNET -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    permission=true
                    Log.d("UploadFragment","Permission granted")
                } else {
                    permission=false
                    Log.d("UploadFragment","Permission Denied")
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }


}
