package com.toucanwalletdemo.data.remote.repositories.mappers

import com.toucanwalletdemo.data.models.Account
import com.toucanwalletdemo.data.models.AccountInfo
import com.toucanwalletdemo.data.remote.backend.response.AccountBackendResponse
import com.toucanwalletdemo.data.remote.backend.response.AccountInfoBackendResponse

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