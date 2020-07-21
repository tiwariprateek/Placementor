package com.example.placementor.upload

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.placementor.*
import com.example.placementor.databinding.FragmentUploadBinding
import com.squareup.picasso.Picasso
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class UploadFragment : Fragment() {
    private lateinit var sharedViewModel: SignUpSharedViewModel
    private lateinit var sharedFactory: SharedViewModelFactory
    lateinit var binding: FragmentUploadBinding
    lateinit var compressedImage:File
    lateinit var cursor: Cursor
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=1001
    var permission=false
    var image:Uri? = null
    var documentUri:Uri?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedFactory=
            SharedViewModelFactory(
                UserRepository(FirebaseSource())
            )
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )

        } else {
            permission=true
        }

        sharedViewModel=ViewModelProvider(requireActivity(),sharedFactory).get(SignUpSharedViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload, container, false)
        binding.uploadviewmodel=sharedViewModel
        binding.lifecycleOwner=this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Upload", "Permission is $permission")
        val dialog =
            LoadingDialog(requireActivity()).buildDialog(requireContext(), requireActivity())
        button.setOnClickListener {
            if (image!=null && documentUri!=null) {
                dialog.show()
                sharedViewModel.uploadImage()
                sharedViewModel.uploadCV()
            }
            else
                Toast.makeText(requireContext(),"Please select image and CV to continue",Toast.LENGTH_SHORT).show()
        }
        sharedViewModel.imageStatus.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { imgstatus ->
                if (imgstatus != null) {
                    sharedViewModel.documentStatus.observe(
                        viewLifecycleOwner, androidx.lifecycle.Observer { docstatus ->
                            if (docstatus != null) {
                                dialog.dismiss()
                                navigateToDashboard()
                            }
                        }
                    )
                }
            })
        imageView5.setOnClickListener {
            if (permission) {
                val intent = Intent().setType("application/pdf")
                    .setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(intent, 1)
            }
            else{
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Without permission we won't be abe to unlock our whole potential." +
                        "Kindly grant the permission to have best experience")
                    .setCancelable(false)
                    .setPositiveButton("OK"
                    ) { _, _ ->
                        requestPermissions(
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                    }
                val alert: AlertDialog = builder.create()
                alert.show()
            }
        }
        imageView4.setOnClickListener {
            if (permission) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_PICK
                startActivityForResult(intent, 0)
            }
            else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Without permission we won't be abe to unlock our whole potential." +
                        "Kindly grant the permission to have best experience")
                    .setCancelable(false)
                    .setPositiveButton("OK"
                    ){ _, _ ->
                            requestPermissions(
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                            )
                        }
                val alert: AlertDialog = builder.create()
                alert.show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            0 -> if (requestCode==0 && resultCode==RESULT_OK && data !=null && data.data!=null ){
                val imageUri=data.data
                val file= MyFileUtil.from(context,imageUri)
                CoroutineScope(IO).launch {
                    compressedImage = Compressor.compress(requireContext(), file){
                        quality(10)
                    }
                    //val image=Uri.fromFile(compressedImage)
                    image = Uri.fromFile(compressedImage)
                    sharedViewModel.imageUri=image
                    Log.d("Image","Image Uri is $image")
                    withContext(Main) {
                        Picasso.get().load(image).into(imageView4)
                    }
                }
            }
            1 ->if (requestCode==1 && resultCode==RESULT_OK && data !=null && data.data!=null){
                //val documentUri=data.data
                documentUri = data.data
                sharedViewModel.documentUri=documentUri
                val documentString=documentUri.toString()
                if (documentString.startsWith("content://")){
                    try {
                        cursor = requireActivity().contentResolver
                            .query(documentUri!!, null, null, null, null)!!
                        if (cursor.moveToFirst()) {
                            val displayName =
                                cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            textView8.text = displayName
                        }
                    }finally {
                        cursor.close()
                    }
                }
            }
        }
    }
    fun navigateToDashboard(){
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        findNavController().navigate(R.id.action_uploadFragment_to_studentDashboardFragment,null,options)
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                permission = (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
