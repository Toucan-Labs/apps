package com.toucanpay.ui.model

data class UserRegistrationData(
    val username: String,
    val pin: String,
    val pinRepeated: String,
    val referCode: String,
    val email: String
)