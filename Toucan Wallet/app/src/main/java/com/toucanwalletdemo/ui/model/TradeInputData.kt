package com.toucanwalletdemo.ui.model

import java.io.Serializable
import java.math.BigDecimal

data class TradeInputData(
    val username: String,
    val tokenSymbol: String,
    val amount: BigDecimal,
    val reference: String
): Serializable