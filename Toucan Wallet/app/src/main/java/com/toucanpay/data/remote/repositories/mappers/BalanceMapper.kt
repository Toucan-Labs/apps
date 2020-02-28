package com.toucanpay.data.remote.repositories.mappers

import com.toucanpay.data.models.Balance
import com.toucanpay.data.remote.backend.response.BalanceBackendResponse
import com.toucanpay.data.remote.backend.response.BalancesBackendResponse

fun BalancesBackendResponse.toBalances(): List<Balance> =
    message.mapNotNull { it.toBalance() }


fun BalanceBackendResponse.toBalance(): Balance? =
    Balance(balance, tokenName, tokenSymbol, colors)