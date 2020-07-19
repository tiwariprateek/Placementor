package com.example.placementor.academic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.placementor.*
import com.example.placementor.databinding.FragmentAcademicBinding
import com.example.placementor.SharedViewModelFactory
import com.example.placementor.SignUpSharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class AcademicFragment : Fragment() {
    private lateinit var binding:FragmentAcademicBinding
    private lateinit var sharedViewModel: SignUpSharedViewModel
    private lateinit var sharedFactory: SharedViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,
            R.layout.fragment_academic,container,false)
        sharedFactory=
            SharedViewModelFactory(
                UserRepository(FirebaseSource())
            )
        sharedViewModel=ViewModelProvider(requireActivity(),sharedFactory).get(SignUpSharedViewModel::class.java)
        binding.academic=sharedViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = binding.academicName.text
        val enroll_no = binding.academicEnroll.text
        val course = binding.academicCourse.text
        val yop = binding.academicYop.text
        val backlogs = binding.academicBacklog.text
        binding.academicButton.setOnClickListener {
            if (name.isEmpty() || enroll_no.isEmpty() || course.isEmpty() || yop.isEmpty() || backlogs.isEmpty()){
                if (name.isEmpty())
                    binding.academicName.error = "Name can't be left empty"
                if (enroll_no.isEmpty())
                    binding.academicEnroll.error = "Enrollment number can't be left empty"
                if (course.isEmpty())
                    binding.academicCourse.error = "Course can't be left empty"
                if (yop.isEmpty())
                    binding.academicYop.error = "Year of passing can't be left empty"
                if (backlogs.isEmpty())
                    binding.academicBacklog.error = "Backlogs can't be left empty"
            }
            else{
                Log.d("Firestore","AcademicFragment values ${sharedViewModel.name}")
                navigateToEducation()
        }
        }
    }
    fun navigateToEducation(){
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        findNavController().navigate(R.id.action_academicFragment_to_educationFragment,null,options)
    }
}
