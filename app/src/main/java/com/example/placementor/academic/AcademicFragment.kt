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
    //private lateinit var viewModel: AcademicViewModel
    private lateinit var sharedViewModel: SignUpSharedViewModel
    //private lateinit var factory: AcademicViewModelFactory
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
        //factory= AcademicViewModelFactory(UserRepository(FirebaseSource()))
        //viewModel=ViewModelProvider(this,factory).get(AcademicViewModel::class.java)
        sharedViewModel=ViewModelProvider(requireActivity(),sharedFactory).get(SignUpSharedViewModel::class.java)
        binding.academic=sharedViewModel
        // Inflate the layout for this fragment
        binding.lifecycleOwner = this
        return binding.root
    }
    fun navigate(){
//        val email= arguments?.let { AcademicFragmentArgs.fromBundle(it).email }
//        val action=AcademicFragmentDirections
////            .actionAcademicFragmentToEducationFragment(viewModel.name!!,viewModel.enrollnumber!!,viewModel.course!!,
////            viewModel.backlogs!!,viewModel.yop!!,email!!)
        val action=
            AcademicFragmentDirections.actionAcademicFragmentToEducationFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val email= arguments?.let { AcademicFragmentArgs.fromBundle(it).email }
        //Log.d("Firestore","Value of email is $email")
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
            Log.d("Firestore","AcademicFragment values ${sharedViewModel.name}")

            navigate()
//            findNavController().navigate(R.id.educationFragment, null, options)
        }
    }

}
