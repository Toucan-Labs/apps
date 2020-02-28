package com.toucanpay.data.remote.backend.request

data class VerifyRequest(
    val email: String?,
    val code: String
)