package com.example.placementor.upload

import android.os.Bundle
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
}
