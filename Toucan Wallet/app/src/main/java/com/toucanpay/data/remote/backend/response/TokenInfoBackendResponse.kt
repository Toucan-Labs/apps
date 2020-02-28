package com.toucanpay.data.remote.backend.response

import com.google.gson.annotations.SerializedName

data class TokenInfoBackendResponse(
    @SerializedName("message") val message: TokenBackendResponse?,
    @SerializedName("error") val error: String?
)

data class TokenListInfoBackendResponse(
    @SerializedName("message") val message: List<TokenBackendResponse>?,
    @SerializedName("error") val error: String?
)

data class TokenBackendResponse(
    @SerializedName("description") val description: String?,
    @SerializedName("issuer") val issuer: String?,
    @SerializedName("restrictionType") val restrictionType: String?,
    @SerializedName("tokenName") val tokenName: String?,
    @SerializedName("tokenSymbol") val tokenSymbol: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("colors") val colors: List<String>?
)