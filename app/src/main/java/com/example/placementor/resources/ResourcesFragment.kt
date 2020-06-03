package com.example.placementor.resources

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.placementor.R
import com.example.placementor.StudentSharedViewModel
import com.example.placementor.StudentSharedViewModelFactory
import com.example.placementor.utils.ItemDecoration
import kotlinx.android.synthetic.main.fragment_resources.*


/**
 * A simple [Fragment] subclass.
 */
class ResourcesFragment : Fragment() {
    lateinit var resourceAdapter: ResourceAdapter
    val resources=ArrayList<Resources>()
    private lateinit var sharedViewModel: StudentSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel= ViewModelProvider(requireActivity()).get(StudentSharedViewModel::class.java)
        sharedViewModel.Resources.observe(viewLifecycleOwner, Observer { resource ->
            resourceAdapter=ResourceAdapter(requireContext(),resources,ResourceAdapter.OnClickListener{
                val url=it.downloadUrl
                val request=DownloadManager.Request(Uri.parse(url))
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE )
                request.setTitle("${it.name}")
                request.setDescription("Downloading Previous year placement papers")
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis())
                val donwloadmanager=requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                donwloadmanager.enqueue(request)

            })
            val decoration= ItemDecoration(30)
            jobs_recyclerview.addItemDecoration(decoration)
            jobs_recyclerview.adapter=resourceAdapter
            jobs_recyclerview.layoutManager= LinearLayoutManager(requireContext())
            jobs_recyclerview.setHasFixedSize(true)
            resources.clear()
            resources.addAll(resource)
            resourceAdapter.notifyDataSetChanged()
            Log.d("Jobs","Value of jobs are ${sharedViewModel.Resources.value}")
        })
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resources, container, false)
    }

}


