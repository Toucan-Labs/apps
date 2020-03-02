package com.toucanwalletdemo.data.remote.backend.response

import com.google.gson.annotations.SerializedName

data class RegistrationRewardBackendResponse(
    @SerializedName("message") val message: RewardBackendResponse?,
    @SerializedName("error") val error: String?
)

data class RewardBackendResponse(
    @SerializedName("secret") val secret: String?,
    @SerializedName("amount") val amount: String?,
    @SerializedName("tokenSymbol") val tokenSymbol: String?,
    @SerializedName("status") val status: String?
)