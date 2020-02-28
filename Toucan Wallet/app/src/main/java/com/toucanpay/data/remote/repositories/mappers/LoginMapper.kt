package com.toucanpay.data.remote.repositories.mappers

import com.toucanpay.data.models.AuthTokens
import com.toucanpay.data.models.Credentials
import com.toucanpay.data.remote.backend.response.AuthTokensBackendResponse
import com.toucanpay.data.remote.backend.response.CredentialsBackendResponse

fun CredentialsBackendResponse.toCredentials() = Credentials(
    message?.toAuthTokens(),
    error
)

fun AuthTokensBackendResponse.toAuthTokens() = AuthTokens(token, privateKey)