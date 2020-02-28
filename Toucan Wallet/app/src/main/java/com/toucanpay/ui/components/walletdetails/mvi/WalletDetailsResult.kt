package com.toucanpay.ui.components.walletdetails.mvi

import com.toucanpay.data.models.Token
import com.toucanpay.data.models.Transaction
import com.toucanpay.ui.base.mvi.MviResult

sealed class WalletDetailsResult: MviResult {

    object InFlight: WalletDetailsResult()

    data class Success(val transactions: List<Transaction>, val token: Token?, val balance: String, val acceptMessages: Boolean): WalletDetailsResult()

    data class Error(val errorMessage: String): WalletDetailsResult()
}