package com.example.placementor.upload

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.placementor.*
import com.example.placementor.databinding.FragmentUploadBinding
import com.example.placementor.SharedViewModelFactory
import com.example.placementor.SignUpSharedViewModel
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
//    lateinit var viewModel: UploadViewModel
    private lateinit var sharedViewModel: SignUpSharedViewModel
    private lateinit var sharedFactory: SharedViewModelFactory
//    lateinit var factory: UploadViewModelFactory
    lateinit var binding: FragmentUploadBinding
    lateinit var compressedImage:File
    lateinit var cursor: Cursor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedFactory=
            SharedViewModelFactory(
                UserRepository(FirebaseSource())
            )
        //factory= UploadViewModelFactory(UserRepository(FirebaseSource()), Application())
        //viewModel=ViewModelProvider(this,factory).get(UploadViewModel::class.java)
        sharedViewModel=ViewModelProvider(requireActivity(),sharedFactory).get(SignUpSharedViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload, container, false)
        binding.progressBar2.visibility=View.GONE
        sharedViewModel.imageStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it!=null) {
                sharedViewModel.documentStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    if (it!=null) {
                        binding.progressBar2.visibility=View.GONE
                        navigateToDashboard()
                    }
                })
            }
        })
        binding.uploadviewmodel=sharedViewModel
        binding.lifecycleOwner=this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            binding.progressBar2.visibility=View.VISIBLE
            sharedViewModel.uploadImage()
            sharedViewModel.uploadCV()
        }
        imageView5.setOnClickListener {
            val intent=Intent().setType("application/pdf")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent,1)
        }
        imageView4.setOnClickListener {
            val intent=Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(intent,0)
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
                    val image=Uri.fromFile(compressedImage)
                    sharedViewModel.imageUri=image
                    Log.d("Image","Image Uri is $image")
                    withContext(Main) {
                        Picasso.get().load(image).into(imageView4)
                    }
                }
            }
            1 ->if (requestCode==1 && resultCode==RESULT_OK && data !=null && data.data!=null){
                val documentUri=data.data
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
        val action =
            UploadFragmentDirections.actionUploadFragmentToStudentDashboardFragment()
        findNavController().navigate(action)
    }
}
