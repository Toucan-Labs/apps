package com.toucanwalletdemo.data.models

import java.io.Serializable

data class Credentials(
    val message: AuthTokens?,
    val error: String?
): Serializable

data class AuthTokens(
    val token: String?,
    val privateKey: String?
): Serializable