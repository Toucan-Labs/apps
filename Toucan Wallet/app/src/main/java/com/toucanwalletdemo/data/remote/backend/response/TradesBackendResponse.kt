package com.toucanwalletdemo.data.remote.backend.response

import com.google.gson.annotations.SerializedName

data class TradesBackendResponse(
    @SerializedName("message") val message: List<TradeBackendResponse>
)

data class TradeBackendResponse(
    @SerializedName("createdAt") val createdAt: Long?,
    @SerializedName("creatorAccountId") val creatorAccountId: String?,
    @SerializedName("expiresAt") val expiresAt: Long?,
    @SerializedName("expirySeconds") val expirySeconds: Long?,
    @SerializedName("inviteeAccountId") val inviteeAccountId: String?,
    @SerializedName("reference") val reference: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("requests") val requests: List<TransactionBackendResponse>?
)