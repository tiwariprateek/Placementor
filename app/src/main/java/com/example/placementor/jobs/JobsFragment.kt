package com.example.placementor.jobs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.placementor.*
import com.example.placementor.utils.ItemDecoration

import kotlinx.android.synthetic.main.fragment_jobs.*

/**
 * A simple [Fragment] subclass.
 */
class JobsFragment : Fragment() {
    //lateinit var factory: JobsViewModelFactory
    //lateinit var viewModel: JobsViewModel
    lateinit var jobsAdapter: JobsAdapter
    private lateinit var sharedViewModel: StudentSharedViewModel
    val jobs=ArrayList<Jobs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //factory= JobsViewModelFactory(UserRepository(FirebaseSource()))
        sharedViewModel=ViewModelProvider(requireActivity()).get(StudentSharedViewModel::class.java)
        sharedViewModel.Jobs.observe(viewLifecycleOwner, Observer { job->
            jobsAdapter=JobsAdapter(requireContext(),jobs,JobsAdapter.OnClickListener{
                sharedViewModel.displayJobsDetails(it)
            })
            val decoration= ItemDecoration(30)
            jobs_recyclerview.addItemDecoration(decoration)
            jobs_recyclerview.adapter=jobsAdapter
            jobs_recyclerview.setHasFixedSize(true)
            jobs.clear()
            jobs.addAll(job)
            jobsAdapter.notifyDataSetChanged()
            Log.d("Jobs","Value of jobs are ${sharedViewModel.Jobs.value}")
            sharedViewModel.navigateToSelectedJob.observe(viewLifecycleOwner, Observer {
                if (it != null){
                    this.findNavController().navigate(JobsFragmentDirections.actionJobsFragmentToJobDetails(it))
                    Log.d("Onclick", "clicked the recyclerview $it")
                    sharedViewModel.displayJobDetailsComplete()
            }
            })
        })
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }

}
