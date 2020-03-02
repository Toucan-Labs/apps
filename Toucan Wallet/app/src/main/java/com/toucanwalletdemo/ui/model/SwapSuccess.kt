package com.toucanwalletdemo.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class SwapSuccess(
    val tokenSuccess: TokenSuccess? = null,
    val message: String?
): Serializable

@Parcelize
data class TokenSuccess(
    val outcomeTokenSymbol: String,
    val outcomeColor: Int,
    val outcomeAmount: String,
    val incomeTokenSymbol: String,
    val incomeColor: Int,
    val incomeAmount: String
): Parcelable