package com.toucanpay.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    val amount: String,
    val createdAt: Long,
    val expiresAt: Long?,
    val expirySeconds: Long?,
    val reference: String,
    val fromAccountId: String,
    val hash: String,
    val status: String,
    val toAccountId: String,
    val requestType: String,
    val tokenSymbol: String,
    val transactionType: String,
    val accountUsername: String?
): Parcelable