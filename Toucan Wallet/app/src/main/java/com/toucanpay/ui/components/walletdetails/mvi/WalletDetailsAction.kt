package com.toucanpay.ui.components.walletdetails.mvi

import com.toucanpay.ui.base.mvi.MviAction

sealed class WalletDetailsAction: MviAction {

    data class GetTransactionsAction(val tokenSymbol: String): WalletDetailsAction()

    data class AcceptMessagesAction(val isChecked: Boolean): WalletDetailsAction()
}