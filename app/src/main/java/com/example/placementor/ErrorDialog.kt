package com.example.placementor
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.errordialog.*

class ErrorDialog():DialogFragment() {
    var args:String ?= null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Login Failed")
        builder.setMessage("Error in login")
        builder.setNeutralButton("Okay",object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dismiss()
            }
        })
        return builder.create()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (arguments != null) {
            args = arguments?.getString("exception")
            error_message.text = args
            Log.d("ErrorDialog", "onCreateView: exception is in if $args ")
        }
        Log.d("ErrorDialog", "onCreateView: exception is $args ")
        return inflater.inflate(R.layout.errordialog, container, false)
        }
    override fun onDestroyView() {
        val dialog = dialog
        if (dialog != null && retainInstance) {
            dialog.setDismissMessage(null)
        }
        super.onDestroyView()
    }

}