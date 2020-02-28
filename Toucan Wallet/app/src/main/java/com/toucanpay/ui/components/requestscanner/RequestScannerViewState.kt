package com.toucanpay.ui.components.requestscanner

import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.requestscanner.mvi.RequestScannerResult
import com.toucanpay.ui.components.requestscanner.mvi.RequestScannerResult.*
import com.toucanpay.ui.model.TradeInputData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestScannerViewState(
    val inProgress: Boolean,
    val success: ViewStateEvent<TradeInputData>?,
    val error: ViewStateEvent<String>?
): MviViewState<RequestScannerResult> {

    companion object {
        fun default() = RequestScannerViewState(false, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: RequestScannerResult): RequestScannerViewState {
        return when (result) {
            is InFlight -> result.reduce()
            is Success -> result.reduce()
            is Error -> result.reduce()
        }
    }

    private fun InFlight.reduce() = this@RequestScannerViewState.copy(
        inProgress = true,
        success = null,
        error = null
    )

    private fun Error.reduce() = this@RequestScannerViewState.copy(
        inProgress = false,
        success = null,
        error = ViewStateEvent(errorMessage)
    )

    private fun Success.reduce() = this@RequestScannerViewState.copy(
        inProgress = false,
        success = ViewStateEvent(data),
        error = null
    )
}