package com.toucanpay.ui.components.walletdetails

import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.walletdetails.mvi.WalletDetailsAction
import com.toucanpay.ui.components.walletdetails.mvi.WalletDetailsActionProcessor
import com.toucanpay.ui.components.walletdetails.mvi.WalletDetailsResult

class WalletDetailsViewModel: MviViewModel<WalletDetailsAction, WalletDetailsResult, WalletDetailsViewState>(
    WalletDetailsActionProcessor(), WalletDetailsViewState.default()
) {

    lateinit var token: String

    override fun initialAction(): WalletDetailsAction? =
        WalletDetailsAction.GetTransactionsAction(token)

    fun onGetTransactions(tokenSymbol: String) {
        token = tokenSymbol
    }

    fun onMarketingAccept(isChecked: Boolean) =
        accept(WalletDetailsAction.AcceptMessagesAction(isChecked))
}