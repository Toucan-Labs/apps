package com.toucanwalletdemo.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class SendTokenData(
    val tokenSymbol: String,
    val amount: BigDecimal,
    val toAccountId: String,
    val reference: String
): Parcelable