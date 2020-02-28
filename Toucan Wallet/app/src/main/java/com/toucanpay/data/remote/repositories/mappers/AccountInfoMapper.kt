package com.toucanpay.data.remote.repositories.mappers

import com.toucanpay.data.models.Account
import com.toucanpay.data.models.AccountInfo
import com.toucanpay.data.remote.backend.response.AccountBackendResponse
import com.toucanpay.data.remote.backend.response.AccountInfoBackendResponse

fun AccountInfoBackendResponse.toAccountInfo() = AccountInfo(
    message?.toAccount(),
    error
)

fun AccountBackendResponse.toAccount(): Account? {
    username ?: return null
    privateKey ?: return null

    return Account(
        username,
        email ?: "",
        privateKey,
        publicKey ?: "",
        referralCode ?: ""
    )
}