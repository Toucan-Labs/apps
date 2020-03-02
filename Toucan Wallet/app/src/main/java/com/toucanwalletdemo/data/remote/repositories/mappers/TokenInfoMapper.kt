package com.toucanwalletdemo.data.remote.repositories.mappers

import com.toucanwalletdemo.data.models.Token
import com.toucanwalletdemo.data.models.TokenInfo
import com.toucanwalletdemo.data.remote.backend.response.TokenBackendResponse
import com.toucanwalletdemo.data.remote.backend.response.TokenInfoBackendResponse
import com.toucanwalletdemo.data.remote.backend.response.TokenListInfoBackendResponse

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