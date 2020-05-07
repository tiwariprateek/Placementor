package com.example.placementor.upload

import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toFile
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.fragment.findNavController
import com.example.placementor.FirebaseSource
import com.example.placementor.MyFileUtil
import com.example.placementor.R
import com.example.placementor.UserRepository
import com.example.placementor.databinding.FragmentUploadBinding
import com.example.placementor.education.EducationFragmentArgs
import com.google.firebase.firestore.util.FileUtil
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
import java.io.FileOutputStream
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class UploadFragment : Fragment() {
    lateinit var viewModel: UploadViewModel
    lateinit var factory: UploadViewModelFactory
    lateinit var binding: FragmentUploadBinding
    lateinit var compressedImage:File
    lateinit var cursor: Cursor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        factory= UploadViewModelFactory(UserRepository(FirebaseSource()), Application())
        viewModel=ViewModelProvider(this,factory).get(UploadViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload, container, false)
        binding.lifecycleOwner=this
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            viewModel.uploadImage()
            viewModel.uploadCV()
            val action = UploadFragmentDirections.actionUploadFragmentToStudentDashboardFragment()
            findNavController().navigate(action)

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
                val file=MyFileUtil.from(context,imageUri)
                CoroutineScope(IO).launch {
                    compressedImage = Compressor.compress(requireContext(), file){
                        quality(50)
                    }
                    val image=Uri.fromFile(compressedImage)
                    viewModel.imageUri=image
                    withContext(Main) {
                        Picasso.get().load(image).into(imageView4)
                    }
                    //viewModel.uploadImage()
                }
            }
            1 ->if (requestCode==1 && resultCode==RESULT_OK && data !=null && data.data!=null){
                val documentUri=data.data
                viewModel.documentUri=documentUri
                //viewModel.uploadCV()
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
}
