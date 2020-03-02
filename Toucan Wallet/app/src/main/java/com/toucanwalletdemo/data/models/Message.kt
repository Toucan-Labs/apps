package com.toucanwalletdemo.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//TODO: Change to Date
@Parcelize
data class Message(
    val id: String,
    val from: String,
    val to: String,
    val content: String,
    val timestamp: Long,
    val accountUsername: String
): Parcelable