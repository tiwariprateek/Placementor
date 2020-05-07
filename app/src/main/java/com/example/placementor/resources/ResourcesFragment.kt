package com.example.placementor.resources

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.placementor.ItemDecoration

import com.example.placementor.R
import com.example.placementor.Resources
import kotlinx.android.synthetic.main.fragment_resources.*

/**
 * A simple [Fragment] subclass.
 */
class ResourcesFragment : Fragment() {
    lateinit var viewModel: ResourceViewModel
    lateinit var resourceAdapter: ResourceAdapter
    val resources=ArrayList<Resources>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel= ViewModelProvider(this).get(ResourceViewModel::class.java)
        viewModel.Resources.observe(viewLifecycleOwner, Observer { resource ->
            resourceAdapter=ResourceAdapter(requireContext(),resources)
            val decoration= ItemDecoration(30)
            jobs_recyclerview.addItemDecoration(decoration)
            jobs_recyclerview.adapter=resourceAdapter
            jobs_recyclerview.layoutManager= LinearLayoutManager(requireContext())
            jobs_recyclerview.setHasFixedSize(true)
            resources.clear()
            resources.addAll(resource)
            resourceAdapter.notifyDataSetChanged()
            Log.d("Jobs","Value of jobs are ${viewModel.Resources.value}")
        })
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resources, container, false)
    }

}
