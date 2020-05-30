package com.example.placementor.jobs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.placementor.R
import kotlinx.android.synthetic.main.job_item.view.*

class JobsAdapter(private val context: Context, private val Jobs: List<Jobs>,val onClickListener: OnClickListener)
    : RecyclerView.Adapter<JobsAdapter.ViewHolder>() {

    // Usually involves inflating a layout from XML and returning the holder - THIS IS EXPENSIVE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.job_item, parent, false))
    }

    // Returns the total count of items in the list
    override fun getItemCount() = Jobs.size

    // Involves populating data into the item through holder - NOT expensive
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
            val course=itemView.job_course.text
            itemView.job_course.setText("$course ${jobs.Course}")
            val salary=itemView.job_salary.text
            itemView.job_salary.setText("$salary ${jobs.Salary}")
            val profile=itemView.job_profile.text
            itemView.job_profile.setText("$profile ${jobs.Profile}")
            val lastdate=itemView.job_lastdate.text
            itemView.job_lastdate.setText("$lastdate ${jobs.LastDate}")
//            itemView.tvAge.text = "Age: ${contact.age}"
//            Glide.with(context).load(contact.imageUrl).into(itemView.ivProfile)
        }
    }
    class OnClickListener(val clickListener: (jobs:Jobs) -> Unit) {
        fun onClick(jobs:Jobs) = clickListener(jobs)
    }
}