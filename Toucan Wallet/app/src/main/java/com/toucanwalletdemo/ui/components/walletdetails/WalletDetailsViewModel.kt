package com.toucanwalletdemo.ui.components.walletdetails

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.walletdetails.mvi.WalletDetailsAction
import com.toucanwalletdemo.ui.components.walletdetails.mvi.WalletDetailsActionProcessor
import com.toucanwalletdemo.ui.components.walletdetails.mvi.WalletDetailsResult

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