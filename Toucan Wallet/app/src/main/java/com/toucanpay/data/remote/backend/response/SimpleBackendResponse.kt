package com.toucanpay.data.remote.backend.response

import com.google.gson.annotations.SerializedName

data class SimpleBackendResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("error") val error: String?
)