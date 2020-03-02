package com.toucanwalletdemo.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trade(
    val createdAt: Long,
    val creatorAccountId: String,
    val expiresAt: Long,
    val expirySeconds: Long,
    val inviteeAccountId: String,
    val reference: String,
    val id: String,
    val requests: List<Transaction>
): Parcelable