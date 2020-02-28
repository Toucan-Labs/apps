package com.toucanpay.data.remote.backend.response

import com.google.gson.annotations.SerializedName


data class AccountInfoBackendResponse(
    @SerializedName("message") val message: AccountBackendResponse?,
    @SerializedName("error") val error: String?
)

data class AccountBackendResponse(
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("privateKey") val privateKey: String?,
    @SerializedName("publicKey") val publicKey: String?,
    @SerializedName("referralCode") val referralCode: String?
)