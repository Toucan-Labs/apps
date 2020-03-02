package com.toucanwalletdemo.data.models

import java.io.Serializable

data class AccountInfo(
    val message: Account?,
    val error: String?
): Serializable

data class Account(
    val username: String,
    val email: String,
    val privateKey: String,
    val publicKey: String,
    val referralCode: String
): Serializable