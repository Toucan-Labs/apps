package com.toucanpay.data.remote.repositories.mappers

import com.toucanpay.data.models.Token
import com.toucanpay.data.models.TokenInfo
import com.toucanpay.data.remote.backend.response.TokenBackendResponse
import com.toucanpay.data.remote.backend.response.TokenInfoBackendResponse
import com.toucanpay.data.remote.backend.response.TokenListInfoBackendResponse

fun TokenInfoBackendResponse.toTokenInfo(): TokenInfo = TokenInfo(
    message?.toToken(),
    error
)

fun TokenListInfoBackendResponse.toTokens(): List<Token>? = message?.mapNotNull { it.toToken() }

fun TokenBackendResponse.toToken(): Token? {
    tokenName ?: return null
    tokenSymbol ?: return null

    return Token(
        description ?: "",
        issuer ?: "",
        restrictionType ?: "",
        tokenName,
        tokenSymbol,
        url ?: "",
        colors ?: listOf()
    )
}