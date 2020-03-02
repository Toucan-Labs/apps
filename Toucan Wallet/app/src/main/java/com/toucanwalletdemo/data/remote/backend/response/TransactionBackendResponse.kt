package com.toucanwalletdemo.data.remote.backend.response

import com.google.gson.annotations.SerializedName

data class TransactionsBackendResponse(
    @SerializedName("message") val message: List<TransactionBackendResponse>
)

data class TransactionBackendResponse(
    @SerializedName("amount") val amount: String?,
    @SerializedName("createdAt") val createdAt: Long?,
    @SerializedName("expiresAt") val expiresAt: Long?,
    @SerializedName("expirySeconds") val expirySeconds: Long?,
    @SerializedName("reference") val reference: String?,
    @SerializedName("fromAccountId") val fromAccountId: String?,
    @SerializedName("hash") val hash: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("toAccountId") val toAccountId: String?,
    @SerializedName("requestType") val requestType: String?,
    @SerializedName("tokenSymbol") val tokenSymbol: String?,
    @SerializedName("transactionType") val transactionType: String?
)