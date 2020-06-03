package com.example.placementor.resources

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
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
    private val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=1001
    var permission=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel= ViewModelProvider(requireActivity()).get(StudentSharedViewModel::class.java)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )

        } else {
            permission=true
        }
        sharedViewModel.Resources.observe(viewLifecycleOwner, Observer { resource ->
            resourceAdapter=ResourceAdapter(requireContext(),resources,ResourceAdapter.OnClickListener{
                if (permission){
                    val url=it.downloadUrl
                    val request=DownloadManager.Request(Uri.parse(url))
                    request.apply {
                        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE )
                        setTitle("${it.name}")
                        setDescription("Downloading Previous year placement papers")
                        allowScanningByMediaScanner()
                        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis())
                    }
                    val downloadmanager=requireActivity().getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                    downloadmanager.enqueue(request)
                }
                else{
                    val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("Without permission we won't be abe to unlock our whole potential." +
                            "Kindly grant the permission to have best experience")
                        .setCancelable(false)
                        .setPositiveButton("OK"
                        ){ _, _ ->
                            requestPermissions(
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                            )
                        }
                    val alert: AlertDialog = builder.create()
                    alert.show()
                }
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
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                permission = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

}


