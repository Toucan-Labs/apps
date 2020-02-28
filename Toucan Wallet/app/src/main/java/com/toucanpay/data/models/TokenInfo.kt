package com.toucanpay.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class TokenInfo(
    val token: Token?,
    val error: String?
): Parcelable

@Parcelize
data class TokenListInfo(
    val tokens: List<Token>?,
    val error: String?
): Parcelable


data class Token(
    val description: String,
    val issuer: String,
    val restrictionType: String,
    val tokenName: String,
    val tokenSymbol: String,
    val url: String,
    val colors: List<String>
): Serializable