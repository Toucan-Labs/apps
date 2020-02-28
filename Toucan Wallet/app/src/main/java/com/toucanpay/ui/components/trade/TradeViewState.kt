package com.toucanpay.ui.components.trade

import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.trade.mvi.TradeResult
import com.toucanpay.ui.components.trade.mvi.TradeResult.*
import com.toucanpay.ui.model.TradeInputData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TradeViewState(
    val inProgress: Boolean,
    val token: String?,
    val success: ViewStateEvent<TradeInputData>?,
    val error: ViewStateEvent<String>?,
    val tokenError: ViewStateEvent<String>?
): MviViewState<TradeResult> {

    companion object {
        fun default() = TradeViewState(false, null, null, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: TradeResult): TradeViewState = when (result) {
        is InFlight -> result.reduce()
        is Success -> result.reduce()
        is FirstTokenResult -> result.reduce()
        is Error -> result.reduce()
        is TokenError -> result.reduce()
    }

    private fun InFlight.reduce() = this@TradeViewState.copy(
        inProgress = true,
        success = null,
        token = null,
        error = null
    )

    private fun FirstTokenResult.reduce() = this@TradeViewState.copy(
        inProgress = false,
        success = null,
        token = token,
        error = null
    )

    private fun Success.reduce() = this@TradeViewState.copy(
        inProgress = false,
        success = ViewStateEvent(data),
        token = null,
        error = null
    )

    private fun Error.reduce() = this@TradeViewState.copy(
        inProgress = false,
        success = null,
        token = null,
        error = ViewStateEvent(errorMessage)
    )

    private fun TokenError.reduce() = this@TradeViewState.copy(
        inProgress = false,
        success = null,
        token = null,
        error = null,
        tokenError = ViewStateEvent(errorMessage)
    )
}