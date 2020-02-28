package com.toucanpay.data.models

import java.io.Serializable

data class RegistrationReward(
    val message: Reward?,
    val error: String?
): Serializable

data class Reward(
    val secret: String,
    val amount: String,
    val tokenSymbol: String,
    val status: String
): Serializable