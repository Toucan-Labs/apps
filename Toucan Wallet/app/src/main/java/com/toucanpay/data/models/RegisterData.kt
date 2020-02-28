package com.toucanpay.data.models

data class RegisterData(
    val email: String,
    val password: String,
    val username: String,
    val invitationReferralCode: String
)