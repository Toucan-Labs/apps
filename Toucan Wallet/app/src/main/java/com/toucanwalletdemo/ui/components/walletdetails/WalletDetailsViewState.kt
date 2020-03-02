package com.toucanwalletdemo.ui.components.walletdetails

import com.toucanwalletdemo.data.models.Token
import com.toucanwalletdemo.data.models.Transaction
import com.toucanwalletdemo.ui.base.mvi.MviViewState
import com.toucanwalletdemo.ui.base.mvi.ViewStateEvent
import com.toucanwalletdemo.ui.components.walletdetails.mvi.WalletDetailsResult
import com.toucanwalletdemo.ui.components.walletdetails.mvi.WalletDetailsResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WalletDetailsViewState(
    val inProgress: Boolean,
    val isChecked: Boolean,
    val transactions: List<Transaction>?,
    val token: Token?,
    val balance: String?,
    val error: ViewStateEvent<String>?
): MviViewState<WalletDetailsResult> {

    companion object {
        fun default() =
            WalletDetailsViewState(inProgress = false, isChecked = false, transactions = null, token = null, balance = null, error = null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: WalletDetailsResult): WalletDetailsViewState = when (result) {
        is InFlight -> result.reduce()
        is Success -> result.reduce()
        is Error -> result.reduce()
    }

    private fun InFlight.reduce() = this@WalletDetailsViewState.copy(
        inProgress = true,
        transactions = null,
        token = null,
        balance = null,
        error = null
    )

    private fun Error.reduce() = this@WalletDetailsViewState.copy(
        inProgress = false,
        transactions = null,
        token = null,
        balance = null,
        error = ViewStateEvent(errorMessage)
    )

    private fun Success.reduce() = this@WalletDetailsViewState.copy(
        inProgress = false,
        transactions = transactions,
        token = token,
        isChecked = acceptMessages,
        balance = balance,
        error = null
    )
}