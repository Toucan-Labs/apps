package com.toucanwalletdemo.ui.components.swaps

import com.toucanwalletdemo.data.models.Token
import com.toucanwalletdemo.data.models.Trade
import com.toucanwalletdemo.ui.base.mvi.MviViewState
import com.toucanwalletdemo.ui.base.mvi.ViewStateEvent
import com.toucanwalletdemo.ui.components.swaps.mvi.SwapsResult
import com.toucanwalletdemo.ui.components.swaps.mvi.SwapsResult.*
import com.toucanwalletdemo.ui.model.SwapError
import com.toucanwalletdemo.ui.model.SwapSuccess
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SwapsViewState(
    val inProgress: Boolean,
    val inProgressTrades: Boolean,
    val trades: List<Trade>?,
    val tokens: List<Token>?,
    val tradeRequestSuccess: ViewStateEvent<SwapSuccess>?,
    val tradeRequestError: ViewStateEvent<SwapError>?,
    val error: ViewStateEvent<String>?
): MviViewState<SwapsResult> {

    companion object {
        fun default() =
            SwapsViewState(
                inProgress = false, inProgressTrades = false, trades = null, tokens = null,
                tradeRequestSuccess = null, tradeRequestError = null, error = null
            )
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: SwapsResult) = when (result) {
        is InFlight -> result.reduce()
        is InFlightTrades -> result.reduce()
        is Success -> result.reduce()
        is Error -> result.reduce()
        is TradeRequestSuccess -> result.reduce()
        is TradeRequestError -> result.reduce()
    }

    private fun InFlight.reduce() = this@SwapsViewState.copy(
        inProgress = true,
        trades = null,
        tokens = null,
        error = null
    )

    private fun Error.reduce() = this@SwapsViewState.copy(
        inProgress = false,
        trades = null,
        tokens = null,
        error = ViewStateEvent(errorMessage)
    )

    private fun Success.reduce() = this@SwapsViewState.copy(
        inProgress = false,
        trades = trades,
        tokens = tokens,
        error = null
    )

    private fun InFlightTrades.reduce() = this@SwapsViewState.copy(
        inProgressTrades = true
    )

    private fun TradeRequestSuccess.reduce() = this@SwapsViewState.copy(
        inProgressTrades = false,
        tradeRequestSuccess = ViewStateEvent(swapSuccess),
        trades = trades,
        tradeRequestError = null
    )

    private fun TradeRequestError.reduce() = this@SwapsViewState.copy(
        inProgressTrades = false,
        tradeRequestSuccess = null,
        tradeRequestError = ViewStateEvent(swapError)
    )
}