package com.toucanwalletdemo.data.remote.repositories.mappers

import com.toucanwalletdemo.data.models.Transaction
import com.toucanwalletdemo.data.remote.backend.response.TransactionBackendResponse
import com.toucanwalletdemo.data.remote.backend.response.TransactionsBackendResponse

fun TransactionsBackendResponse.toTransactions(username: String?): List<Transaction> =
    message.mapNotNull { it.toTransaction(username) }

fun TransactionBackendResponse.toTransaction(username: String?): Transaction? {
    createdAt ?: return null
    username ?: return null

    return Transaction(
        amount ?: "0",
        createdAt,
        expiresAt,
        expirySeconds,
        reference ?: "",
        fromAccountId ?: "",
        hash ?: "",
        status ?: "",
        toAccountId ?: "",
        requestType ?: "",
        tokenSymbol ?: "",
        transactionType ?: "",
        username
    )
}