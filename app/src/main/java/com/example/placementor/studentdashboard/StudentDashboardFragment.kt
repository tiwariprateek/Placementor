package com.example.placementor.studentdashboard

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
import com.example.placementor.*

import com.example.placementor.databinding.FragmentStudentDashboardBinding
import com.example.placementor.StudentSharedViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class StudentDashboardFragment : Fragment() {
    private lateinit var binding:FragmentStudentDashboardBinding
//    private lateinit var viewModel: DashboardViewModel
//    private lateinit var factory: DashboardViewModelFactory
    private lateinit var sharedFactory: StudentSharedViewModelFactory
    private lateinit var sharedViewModel: StudentSharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Log.d("Login","User id is ${FirebaseSource().getUid()}")
        // Inflate the layout for this fragment
        binding=
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_student_dashboard,container,false)
        binding.progressBar.visibility=View.VISIBLE
//        factory= DashboardViewModelFactory(UserRepository(FirebaseSource()))
//        viewModel=ViewModelProvider(requireActivity(),factory).get(DashboardViewModel::class.java)
        sharedFactory=
            StudentSharedViewModelFactory(
                UserRepository(FirebaseSource())
            )
        sharedViewModel=ViewModelProvider(requireActivity(),sharedFactory).get(
            StudentSharedViewModel::class.java)
        sharedViewModel.document.observe(viewLifecycleOwner, Observer {
            if (it!=null)
                binding.progressBar.visibility=View.GONE

        })
//        sharedViewModel.imageURL.observe(viewLifecycleOwner, Observer { imageUri ->
//            if (imageUri == null) {
//                Log.d("ImageURI", "ImageUri is null")
//            }
//            else
//                Log.d("ImageURI", "ImageUri is not null")
//            //Log.d("Login","Value of students are ${viewModel.getStudent()}")
//        })
        binding.dashboardviewmodel=sharedViewModel
        binding.lifecycleOwner = this
        Log.d("ViewModel","Values ${sharedViewModel.name.value}")
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
