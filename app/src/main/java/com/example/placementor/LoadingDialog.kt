package com.example.placementor

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater

class LoadingDialog(
    activity: Activity) {
    lateinit var dialog: AlertDialog
    fun buildDialog(context: Context,activity: Activity):AlertDialog{
        val alertDialog=AlertDialog.Builder(context)
        val inflater=activity.layoutInflater
        alertDialog.setView(inflater.inflate(R.layout.custom_progressbar,null))
        dialog=alertDialog.create()
        alertDialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
    fun showDialog(context: Context,activity: Activity){
        val alertDialog=AlertDialog.Builder(context)
        val inflater=activity.layoutInflater
        alertDialog.setView(inflater.inflate(R.layout.custom_progressbar,null))
        dialog=alertDialog.create()
        alertDialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
    fun hideDialog(){
        dialog.dismiss()

    }
}