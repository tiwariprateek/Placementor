package com.example.placementor.jobs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.placementor.R
import kotlinx.android.synthetic.main.fragment_job_apply.*


class JobApplyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_apply, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val joblink = JobApplyFragmentArgs.fromBundle(
            requireArguments()).jobLink
        Log.d("JobApply", "onViewCreated: $joblink")
        jobs_webview.setWebViewClient(WebViewClient())
        jobs_webview.loadUrl(joblink)
    }
}
