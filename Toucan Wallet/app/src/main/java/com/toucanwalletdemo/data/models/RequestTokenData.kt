package com.toucanwalletdemo.data.models

import java.math.BigDecimal

data class RequestTokenData(
    val signature: String,
    val random: String,
    val username: String,
    val amount: BigDecimal,
    val tokenSymbol: String,
    val reference: String
)