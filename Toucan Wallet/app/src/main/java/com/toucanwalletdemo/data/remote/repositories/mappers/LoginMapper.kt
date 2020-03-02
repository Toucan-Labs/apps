package com.toucanwalletdemo.data.remote.repositories.mappers

import com.toucanwalletdemo.data.models.AuthTokens
import com.toucanwalletdemo.data.models.Credentials
import com.toucanwalletdemo.data.remote.backend.response.AuthTokensBackendResponse
import com.toucanwalletdemo.data.remote.backend.response.CredentialsBackendResponse

fun CredentialsBackendResponse.toCredentials() = Credentials(
    message?.toAuthTokens(),
    error
)

fun AuthTokensBackendResponse.toAuthTokens() = AuthTokens(token, privateKey)