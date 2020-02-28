package com.toucanpay.data.remote.backend.request

data class ChangePinRequest(
    val oldPassword: String,
    val newPassword: String
)