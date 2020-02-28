package com.toucanpay.data.remote.backend.request

data class SignatureTokenRequest(
    val signature: String,
    val random: String,
    val username: String,
    val amount: String,
    val tokenSymbol: String,
    val reference: String
)