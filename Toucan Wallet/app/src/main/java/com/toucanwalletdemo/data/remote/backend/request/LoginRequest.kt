package com.toucanwalletdemo.data.remote.backend.request

data class LoginRequest(
    val identifier: String?,
    val password: String
)