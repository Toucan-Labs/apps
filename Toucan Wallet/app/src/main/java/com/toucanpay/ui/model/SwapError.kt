package com.toucanpay.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class SwapError(
    val tokenError: TokenError? = null,
    val message: String?
): Serializable

@Parcelize
data class TokenError(
    val tokenSymbol: String,
    val tokenColor: Int
): Parcelable