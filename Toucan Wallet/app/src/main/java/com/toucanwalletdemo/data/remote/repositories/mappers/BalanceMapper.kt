package com.toucanwalletdemo.data.remote.repositories.mappers

import com.toucanwalletdemo.data.models.Balance
import com.toucanwalletdemo.data.remote.backend.response.BalanceBackendResponse
import com.toucanwalletdemo.data.remote.backend.response.BalancesBackendResponse

fun BalancesBackendResponse.toBalances(): List<Balance> =
    message.mapNotNull { it.toBalance() }


fun BalanceBackendResponse.toBalance(): Balance? =
    Balance(balance, tokenName, tokenSymbol, colors)