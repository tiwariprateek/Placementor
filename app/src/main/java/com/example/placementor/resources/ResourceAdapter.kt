package com.example.placementor.resources

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.placementor.R
import com.example.placementor.jobs.Jobs
import com.example.placementor.jobs.JobsAdapter
import kotlinx.android.synthetic.main.resources_row.view.*

class ResourceAdapter(private val context: Context, private val resources: List<Resources>,val onClickListener: ResourceAdapter.OnClickListener)
    : RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {

    // Usually involves inflating a layout from XML and returning the holder - THIS IS EXPENSIVE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.resources_row, parent, false))
    }

    // Returns the total count of items in the list
    override fun getItemCount() = resources.size

    // Involves populating data into the item through holder - NOT expensive
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = resources[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(contact)
        }
        holder.bind(contact)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resources: Resources) {
            itemView.resource_name.text=resources.name
//            itemView.tvAge.text = "Age: ${contact.age}"
//            Glide.with(context).load(contact.imageUrl).into(itemView.ivProfile)
        }
    }
    class OnClickListener(val clickListener: (resources:Resources) -> Unit) {
        fun onClick(resources: Resources) = clickListener(resources)
    }
}