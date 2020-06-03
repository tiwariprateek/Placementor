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
    private lateinit var sharedFactory: StudentSharedViewModelFactory
    private lateinit var sharedViewModel: StudentSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_student_dashboard, container, false
            )
        val loadingDialog=LoadingDialog(requireActivity())

        loadingDialog.showDialog(requireContext(),requireActivity())
        //binding.progressBar.visibility = View.VISIBLE
        sharedFactory =
            StudentSharedViewModelFactory(
                UserRepository(FirebaseSource())
            )
        sharedViewModel = ViewModelProvider(requireActivity(), sharedFactory).get(
            StudentSharedViewModel::class.java
        )
        sharedViewModel.document.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                loadingDialog.hideDialog()

                //binding.progressBar.visibility = View.GONE
                saveDataLocally(it.getString("name"),it.getString("enrollnumber"),it.getString("course")
                ,it.getString("graduation"),it.getString("ImageURL"))
            }
        })
        binding.dashboardviewmodel = sharedViewModel
        binding.lifecycleOwner = this
        Log.d("ViewModel", "Values ${sharedViewModel.name.value}")

        return binding.root

    }
    fun saveDataLocally(name:String?,enroll:String?,course:String?,academics:String?,imageUri:String?){
        val sharedPref=requireContext().getSharedPreferences("Shared_Pref",Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("name",name)
            putString("enroll",enroll)
            putString("course",course)
            putString("academics",academics)
            putString("imageUri",imageUri)
            commit()
        }
        Log.d("Fragment", "Shared values are $name $enroll $course $academics $imageUri")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireContext().getSharedPreferences("Shared_Pref", Context.MODE_PRIVATE)
        Log.d("Fragment","Fragment created")
        if (sharedViewModel.uid != null) {
            Log.d("Fragment","name ${sharedViewModel.name.value}")
            val name = sharedPref.getString("name", null)
            val enroll = sharedPref.getString("enroll", null)
            val course = sharedPref.getString("course", null)
            val academics = sharedPref.getString("academics", null)
            val imageUri = sharedPref.getString("imageUri", null)
            Log.d("Fragment", "Shared values are $name $enroll $course $academics $imageUri")
            //sharedViewModel.setValues(name!!,enroll!!,course!!,academics!!,imageUri!!)
        }
    }

}
