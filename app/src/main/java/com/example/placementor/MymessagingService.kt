package com.example.placementor

import android.icu.text.CaseMap
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MymessagingService:FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        showNotification(p0.notification?.title!!,p0.notification?.body!!)

    }
    fun showNotification(title: String,message:String){
        val builder=NotificationCompat.Builder(this,"Mynotifications")
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText(message)
            .setAutoCancel(true)
        val manager=NotificationManagerCompat.from(this)
        manager.notify(999,builder.build())

}}