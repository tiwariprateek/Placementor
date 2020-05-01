package com.example.placementor.upload

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.placementor.FirebaseSource
import com.example.placementor.R
import com.example.placementor.UserRepository
import com.example.placementor.databinding.FragmentUploadBinding
import kotlinx.android.synthetic.main.fragment_upload.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class UploadFragment : Fragment() {
    lateinit var viewModel: UploadViewModel
    lateinit var factory: UploadViewModelFactory
    lateinit var binding: FragmentUploadBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        factory= UploadViewModelFactory(UserRepository(FirebaseSource()))
        viewModel=ViewModelProvider(this,factory).get(UploadViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload, container, false)
        binding.lifecycleOwner=this
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView4.setOnClickListener {
            val intent=Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent,0)


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0 && resultCode==RESULT_OK && data !=null && data.data==null ){
            val imageUri=data.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source=ImageDecoder.createSource(activity?.contentResolver!!,imageUri!!)
                val drawable=ImageDecoder.decodeDrawable(source)
                imageView4.setImageDrawable(drawable)
            }
            else {
                val bitmap=MediaStore.Images.Media.getBitmap(activity?.contentResolver!!,imageUri)
            }

        }
    }
}
