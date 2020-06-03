package com.example.placementor.resources

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Resources(var name:String=" ", var downloadUrl:String=" "):Parcelable {
    }