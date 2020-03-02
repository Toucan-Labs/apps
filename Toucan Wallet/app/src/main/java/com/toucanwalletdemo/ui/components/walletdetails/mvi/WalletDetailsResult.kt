package com.toucanwalletdemo.ui.components.walletdetails.mvi

import com.toucanwalletdemo.data.models.Token
import com.toucanwalletdemo.data.models.Transaction
import com.toucanwalletdemo.ui.base.mvi.MviResult

sealed class WalletDetailsResult: MviResult {

    object InFlight: WalletDetailsResult()

    data class Success(val transactions: List<Transaction>, val token: Token?, val balance: String, val acceptMessages: Boolean): WalletDetailsResult()

    data class Error(val errorMessage: String): WalletDetailsResult()
}