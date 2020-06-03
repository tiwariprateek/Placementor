package com.example.placementor.education

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.placementor.*
import com.example.placementor.databinding.FragmentEducationBinding
import com.example.placementor.SharedViewModelFactory
import com.example.placementor.SignUpSharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class EducationFragment : Fragment() {
    private lateinit var sharedViewModel: SignUpSharedViewModel
    private lateinit var sharedFactory: SharedViewModelFactory
    lateinit var binding:FragmentEducationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedFactory=
            SharedViewModelFactory(
                UserRepository(FirebaseSource())
            )
        binding=DataBindingUtil
            .inflate(inflater,R.layout.fragment_education,container,false)
        sharedViewModel=ViewModelProvider(requireActivity(),sharedFactory).get(SignUpSharedViewModel::class.java)
        binding.educationviewmodel=sharedViewModel
        binding.lifecycleOwner=this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog=LoadingDialog(requireActivity()).buildDialog(requireContext(),requireActivity())
        binding.academicButton.setOnClickListener {
            sharedViewModel.saveUserData()
            dialog.show()
            Log.d("Firestore","Values are ${sharedViewModel.email}, ${sharedViewModel.name}" +
                    ", ${sharedViewModel.course}, ${sharedViewModel.enrollnumber}, ${sharedViewModel.backlogs}," +
                    "${sharedViewModel.yop}, ${sharedViewModel.xii}, ${sharedViewModel.x}, ${sharedViewModel.backlogs}")
            }
        sharedViewModel.status.observe(viewLifecycleOwner, Observer { datastatus ->
            if (datastatus==true) {
                dialog.dismiss()
                navigateToUpload()
                Log.d("DataStatus","Status is $datastatus")
            }
            Log.d("DataStatus","Status is $datastatus")
        })
    }
    fun navigateToUpload(){
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        findNavController().navigate(R.id.action_educationFragment_to_uploadFragment,null,options)
    }


}
