package com.example.placementor.jobs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.placementor.R
import kotlinx.android.synthetic.main.job_item.view.*

class JobsAdapter(private val Jobs: List<Jobs>,val onClickListener: OnClickListener)
    : RecyclerView.Adapter<JobsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.job_item, parent, false))
    }

    override fun getItemCount() = Jobs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = Jobs[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(contact)
        }
        holder.bind(contact)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(jobs: Jobs) {
            itemView.job_company.text = jobs.Company
            itemView.job_course.setText("Course : ${jobs.Course}")
            itemView.job_salary.setText("Salary : ${jobs.Salary}")
            itemView.job_profile.setText("Profile : ${jobs.Profile}")
            itemView.job_lastdate.setText("Last Date : ${jobs.LastDate}")

        }
    }
    class OnClickListener(val clickListener: (jobs:Jobs) -> Unit) {
        fun onClick(jobs:Jobs) = clickListener(jobs)
    }
}