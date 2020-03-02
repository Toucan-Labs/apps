package com.toucanwalletdemo.data.remote.backend.request

data class ChangePinRequest(
    val oldPassword: String,
    val newPassword: String
)