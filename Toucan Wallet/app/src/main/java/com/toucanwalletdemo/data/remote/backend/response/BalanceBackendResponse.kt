package com.toucanwalletdemo.data.remote.backend.response

import com.google.gson.annotations.SerializedName

data class BalancesBackendResponse(
    @SerializedName("message") val message: List<BalanceBackendResponse>
)

data class BalanceBackendResponse(
    @SerializedName("balance") val balance: String,
    @SerializedName("tokenName") val tokenName: String,
    @SerializedName("tokenSymbol") val tokenSymbol: String,
    @SerializedName("colors") val colors: List<String>
)