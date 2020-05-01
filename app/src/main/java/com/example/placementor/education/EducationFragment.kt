package com.example.placementor.education

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.placementor.FirebaseSource
import com.example.placementor.R
import com.example.placementor.UserRepository
import com.example.placementor.databinding.FragmentEducationBinding
import kotlinx.android.synthetic.main.fragment_education.view.*

/**
 * A simple [Fragment] subclass.
 */
class EducationFragment : Fragment() {
    lateinit var factory: EducationViewModelFactory
    lateinit var viewModel: EducationViewModel
    lateinit var binding:FragmentEducationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        factory= EducationViewModelFactory(UserRepository(FirebaseSource()))
        viewModel=ViewModelProvider(this,factory).get(EducationViewModel::class.java)
        binding=DataBindingUtil
            .inflate(inflater,R.layout.fragment_education,container,false)
        binding.lifecycleOwner=this
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_education, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name= arguments?.let { EducationFragmentArgs.fromBundle(it).name }
        val course= arguments?.let { EducationFragmentArgs.fromBundle(it).course }
        val enrollnumber= arguments?.let { EducationFragmentArgs.fromBundle(it).enrollnumber }
        val backlogs= arguments?.let { EducationFragmentArgs.fromBundle(it).backlogs }
        val yop= arguments?.let { EducationFragmentArgs.fromBundle(it).yop }
        val email= arguments?.let { EducationFragmentArgs.fromBundle(it).email }
        Log.d("Firestore","Values are $email, $name, $course, $enrollnumber, $backlogs, $yop}")
        viewModel.name=name
        viewModel.email=email
        viewModel.course=course
        viewModel.enroll=enrollnumber
        viewModel.backlogs=backlogs
        viewModel.yop=yop
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        val button=view.findViewById<Button>(R.id.academic_button)
        button.setOnClickListener {
            val graduation=view.academic_name.text.toString()
            val xii=view.academic_enroll.text.toString()
            val x=view.academic_course.text.toString()

            viewModel.graduation=graduation
            viewModel.xii=xii
            viewModel.x=x
            Log.d("Firestore","data from EducationFragment is $graduation")
            viewModel.saveUserData()
            findNavController().navigate(R.id.uploadFragment, null, options)
        }
    }

}
