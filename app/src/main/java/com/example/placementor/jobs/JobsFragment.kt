package com.example.placementor.jobs

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.placementor.*

import com.example.placementor.studentdashboard.DashboardViewModel
import com.example.placementor.studentdashboard.DashboardViewModelFactory
import kotlinx.android.synthetic.main.fragment_jobs.*
import kotlinx.android.synthetic.main.fragment_jobs.view.*
import kotlinx.android.synthetic.main.fragment_jobs.view.jobs_recyclerview
import kotlinx.android.synthetic.main.job_item.view.*

/**
 * A simple [Fragment] subclass.
 */
class JobsFragment : Fragment() {
    //lateinit var factory: JobsViewModelFactory
    lateinit var viewModel: JobsViewModel
    lateinit var jobsAdapter: JobsAdapter
    val jobs=ArrayList<Jobs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //factory= JobsViewModelFactory(UserRepository(FirebaseSource()))
        viewModel=ViewModelProvider(this).get(JobsViewModel::class.java)
        viewModel.Jobs.observe(viewLifecycleOwner, Observer { job ->
            jobsAdapter=JobsAdapter(requireContext(),jobs)
            val decoration= ItemDecoration(30)
            jobs_recyclerview.addItemDecoration(decoration)
            jobs_recyclerview.adapter=jobsAdapter
            jobs_recyclerview.layoutManager=LinearLayoutManager(requireContext())
            jobs_recyclerview.setHasFixedSize(true)
            jobs.clear()
            jobs.addAll(job)
            jobsAdapter.notifyDataSetChanged()
            Log.d("Jobs","Value of jobs are ${viewModel.Jobs.value}")
        })
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

}
