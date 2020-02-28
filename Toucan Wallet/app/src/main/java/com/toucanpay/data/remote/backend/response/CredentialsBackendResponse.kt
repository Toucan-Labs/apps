package com.toucanpay.data.remote.backend.response

import com.google.gson.annotations.SerializedName

data class CredentialsBackendResponse(
    @SerializedName("message") val message: AuthTokensBackendResponse?,
    @SerializedName("error") val error: String?
)

data class AuthTokensBackendResponse(
    @SerializedName("token") val token: String?,
    @SerializedName("privateKey") val privateKey: String?
)