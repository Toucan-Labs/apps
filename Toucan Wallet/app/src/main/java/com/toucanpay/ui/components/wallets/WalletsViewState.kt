package com.toucanpay.ui.components.wallets

import com.toucanpay.data.models.Balance
import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.wallets.mvi.WalletsResult
import com.toucanpay.ui.components.wallets.mvi.WalletsResult.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WalletsViewState(
    val inProgress: Boolean,
    val balances: List<Balance>?,
    val error: ViewStateEvent<String>?
): MviViewState<WalletsResult> {

    companion object {
        fun default() = WalletsViewState(false, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: WalletsResult): WalletsViewState = when (result) {
        is InFlight -> result.reduce()
        is Success -> result.reduce()
        is Error -> result.reduce()
    }

    private fun InFlight.reduce() = this@WalletsViewState.copy(
        inProgress = true,
        error = null
    )

    private fun Success.reduce() = this@WalletsViewState.copy(
        inProgress = false,
        balances = balances,
        error = null
    )

    private fun Error.reduce() = this@WalletsViewState.copy(
        inProgress = false,
        error = ViewStateEvent(error)
    )
}