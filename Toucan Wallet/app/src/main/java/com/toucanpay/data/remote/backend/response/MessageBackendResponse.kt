package com.toucanpay.data.remote.backend.response

import com.google.gson.annotations.SerializedName

data class MessagesBackendResponse(
    @SerializedName("message") val message: List<MessageBackendResponse>
)

data class MessageBackendResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    @SerializedName("message") val content: String,
    @SerializedName("createdAt") val timestamp: Long
)