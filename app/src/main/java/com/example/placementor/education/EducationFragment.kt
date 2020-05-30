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
//    private lateinit var factory: EducationViewModelFactory
//    private lateinit var viewModel: EducationViewModel
    private lateinit var sharedViewModel: SignUpSharedViewModel
    private lateinit var sharedFactory: SharedViewModelFactory
    lateinit var binding:FragmentEducationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //factory= EducationViewModelFactory(UserRepository(FirebaseSource()))
        //viewModel=ViewModelProvider(this,factory).get(EducationViewModel::class.java)
        sharedFactory=
            SharedViewModelFactory(
                UserRepository(FirebaseSource())
            )
        binding=DataBindingUtil
            .inflate(inflater,R.layout.fragment_education,container,false)
        sharedViewModel=ViewModelProvider(requireActivity(),sharedFactory).get(SignUpSharedViewModel::class.java)

        binding.educationviewmodel=sharedViewModel
        binding.lifecycleOwner=this
        sharedViewModel.status.observe(viewLifecycleOwner, Observer { datastatus ->
            if (datastatus==true) {
                navigateToUpload()
                Log.d("DataStatus","Status is $datastatus")
            }
            Log.d("DataStatus","Status is $datastatus")
        })
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val name= arguments?.let { EducationFragmentArgs.fromBundle(it).name }
//        val course= arguments?.let { EducationFragmentArgs.fromBundle(it).course }
//        val enrollnumber= arguments?.let { EducationFragmentArgs.fromBundle(it).enrollnumber }
//        val backlogs= arguments?.let { EducationFragmentArgs.fromBundle(it).backlogs }
//        val yop= arguments?.let { EducationFragmentArgs.fromBundle(it).yop }
//        val email= arguments?.let { EducationFragmentArgs.fromBundle(it).email }
//        Log.d("Firestore","Values are $email, $name, $course, $enrollnumber, $backlogs, $yop}")
//        viewModel.name=name
////        viewModel.email=email
////        viewModel.course=course
////        viewModel.enroll=enrollnumber
////        viewModel.backlogs=backlogs
////        viewModel.yop=yop

        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        binding.academicButton.setOnClickListener {
//            val graduation=view.academic_name.text.toString()
//            val xii=view.academic_enroll.text.toString()
//            val x=view.academic_course.text.toString()
//
//            viewModel.graduation=graduation
//            viewModel.xii=xii
//            viewModel.x=x
            //Log.d("Firestore","data from EducationFragment is $graduation")
            Log.d("Firestore","Values are ${sharedViewModel.email}, ${sharedViewModel.name}" +
                    ", ${sharedViewModel.course}, ${sharedViewModel.enrollnumber}, ${sharedViewModel.backlogs}," +
                    "${sharedViewModel.yop}, ${sharedViewModel.xii}, ${sharedViewModel.x}, ${sharedViewModel.backlogs}")
            //sharedViewModel.saveUserData()
            //findNavController().navigate(R.id.action_educationFragment_to_uploadFragment,null,options)
        }
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
