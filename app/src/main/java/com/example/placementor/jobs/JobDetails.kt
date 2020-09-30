package com.example.placementor.jobs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.placementor.R
import kotlinx.android.synthetic.main.fragment_job_details.*


/**
 * A simple [Fragment] subclass.
 */
class JobDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_job_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jobs= JobDetailsArgs.fromBundle(
            requireArguments()
        ).selectedJob
        company_name.setText(jobs.Company)
        company_details.setText(jobs.About)
        job_profile.setText(jobs.Profile)
        job_description.setText(jobs.Description)
        course.setText(jobs.Course)
        textView27.setText(jobs.Eligibility)
        job_salary.setText(jobs.Salary)
        job_website.setText(jobs.Link)
        lastdate.setText(jobs.LastDate)
        online_test_date.setText(jobs.Online_test_date)
        interview_date.setText(jobs.Interview_date)
        textView36.setText(jobs.Special_instructions)
        button2.setOnClickListener {
            this.findNavController().navigate(JobDetailsDirections.actionJobDetailsToJobApplyFragment(jobs.Link))
        }
    }
}
