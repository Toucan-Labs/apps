package com.toucanpay.data.remote.repositories.mappers

import com.toucanpay.data.models.Trade
import com.toucanpay.data.remote.backend.response.TradeBackendResponse
import com.toucanpay.data.remote.backend.response.TradesBackendResponse

fun TradesBackendResponse.toTrades(username: String?): List<Trade> =
    message.mapNotNull { it.toTrade(username) }

fun TradeBackendResponse.toTrade(username: String?): Trade? {
    createdAt ?: return null
    creatorAccountId ?: return null

    return Trade(
        createdAt,
        creatorAccountId,
        expiresAt ?: 0,
        expirySeconds ?: 0,
        inviteeAccountId ?: "",
        reference ?: "",
        id ?: "",
        requests?.mapNotNull { it.toTransaction(username) } ?: listOf()
    )
}