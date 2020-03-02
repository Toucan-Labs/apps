package com.toucanwalletdemo.utils.utils

import android.content.Context
import com.toucanwalletdemo.data.models.Token
import com.toucanwalletdemo.data.models.Transaction
import com.toucanwalletdemo.ui.model.TokenError
import com.toucanwalletdemo.ui.model.TokenSuccess

fun getOutcomeTokenSymbol(transactions: List<Transaction>) =
    if (transactions[0].toAccountId == transactions[0].accountUsername) {
        transactions[1].tokenSymbol
    } else {
        transactions[0].tokenSymbol
    }

fun getTransactionPositions(transactions: List<Transaction>): Pair<Int, Int> = Pair(
    if (transactions[0].toAccountId == transactions[0].accountUsername) 1 else 0, //outcome
    if (transactions[0].toAccountId == transactions[0].accountUsername) 0 else 1 //income
)

fun getTokenError(context: Context, transactions: List<Transaction>, tokens: List<Token>?) =
    TokenError(
        getOutcomeTokenSymbol(transactions),
        getTokenColor(context, transactions, tokens, getTransactionPositions(transactions).first)
    )

fun getTokenSuccess(context: Context, transactions: List<Transaction>, tokens: List<Token>?): TokenSuccess {

    val pos = getTransactionPositions(transactions)

    val outcomeTokenSymbol = transactions[pos.first].tokenSymbol
    val outcomeColor = getTokenColor(context, transactions, tokens, pos.first)
    val outcomeAmount = transactions[pos.first].amount
    val incomeTokenSymbol = transactions[pos.second].tokenSymbol
    val incomeColor = getTokenColor(context, transactions, tokens, pos.second)
    val incomeAmount = transactions[pos.second].amount

    return TokenSuccess(outcomeTokenSymbol, outcomeColor, outcomeAmount, incomeTokenSymbol, incomeColor, incomeAmount)
}