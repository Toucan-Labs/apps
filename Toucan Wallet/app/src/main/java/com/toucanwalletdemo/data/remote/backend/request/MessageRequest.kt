package com.toucanwalletdemo.data.remote.backend.request

data class MessageRequest(
    val toAccount: String,
    val message: String
)