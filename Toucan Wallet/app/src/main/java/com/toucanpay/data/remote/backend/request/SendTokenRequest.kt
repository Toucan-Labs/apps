package com.toucanpay.data.remote.backend.request

data class SendTokenRequest(
    val tokenSymbol: String,
    val amount: String,
    val toAccountId: String,
    val reference: String
)