package com.example.placementor.studentdashboard

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
        Log.d("Login","User id is ${FirebaseSource().getUid()}")
        // Inflate the layout for this fragment
        binding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_student_dashboard,container,false)
        factory= DashboardViewModelFactory(UserRepository(FirebaseSource()))
        viewModel=ViewModelProvider(this,factory).get(DashboardViewModel::class.java)
        binding.dashboardviewmodel=viewModel
        binding.lifecycleOwner = this
        //viewModel.getData()
        viewModel.document.observe(viewLifecycleOwner, Observer { document ->
            if (document!=null)
                Log.d("Login","We are observing livedata ${viewModel.getname()}")
        })
        return binding.root
    }

}
