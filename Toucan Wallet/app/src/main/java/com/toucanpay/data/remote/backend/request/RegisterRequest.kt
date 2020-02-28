package com.toucanpay.data.remote.backend.request

data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String,
    val invitationReferralCode: String
)