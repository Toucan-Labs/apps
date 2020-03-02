package com.toucanwalletdemo.data.remote.backend.request

data class VerifyRequest(
    val email: String?,
    val code: String
)