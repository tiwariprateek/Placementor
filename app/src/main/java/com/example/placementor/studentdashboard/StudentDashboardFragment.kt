package com.example.placementor.studentdashboard

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.placementor.FirebaseSource

import com.example.placementor.R
import com.example.placementor.UserRepository
import com.example.placementor.databinding.FragmentStudentDashboardBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_student_dashboard.*

/**
 * A simple [Fragment] subclass.
 */
class StudentDashboardFragment : Fragment() {
    private lateinit var binding:FragmentStudentDashboardBinding
    private lateinit var viewModel: DashboardViewModel
    private lateinit var factory: DashboardViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Log.d("Login","User id is ${FirebaseSource().getUid()}")
        // Inflate the layout for this fragment
        binding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_student_dashboard,container,false)
        factory= DashboardViewModelFactory(UserRepository(FirebaseSource()))
        viewModel=ViewModelProvider(requireActivity(),factory).get(DashboardViewModel::class.java)
        viewModel.imageURL.observe(viewLifecycleOwner, Observer {imageUri->
            Log.d("Login","Value of students are ${viewModel.getStudent()}")
            if (imageView.drawable==null)
                viewModel.setImage(imageView)
            else
                Log.d("Login","ImageView was not null")

        })
        binding.dashboardviewmodel=viewModel
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Fragment","Activity Created")

    }

    override fun onPause() {
        super.onPause()
        Log.d("Fragment","Fragment Paused")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Fragment","Fragment created")
        //Log.d("Login","Image uri is ${viewModel.imageURL.value}")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Fragment","Fragment started")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Fragment","Fragment resumed")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("Fragment","Fragment Attach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("Fragment","Fragment Detach")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Fragment","Fragment stopped")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Fragment","Fragment destroyed")
    }
}
