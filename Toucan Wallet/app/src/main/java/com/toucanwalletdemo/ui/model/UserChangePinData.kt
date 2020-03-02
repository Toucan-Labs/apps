package com.toucanwalletdemo.ui.model

data class UserChangePinData(
    val emailPin: String,
    val pin: String,
    val pinRepeated: String
)