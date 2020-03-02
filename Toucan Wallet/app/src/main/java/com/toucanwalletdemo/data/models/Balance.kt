package com.toucanwalletdemo.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Balance(
    val balance: String,
    val tokenName: String,
    val tokenSymbol: String,
    val colors: List<String>
): Parcelable