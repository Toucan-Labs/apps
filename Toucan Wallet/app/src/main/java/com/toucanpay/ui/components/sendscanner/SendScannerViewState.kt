package com.toucanpay.ui.components.sendscanner

import com.toucanpay.ui.base.mvi.MviViewState
import com.toucanpay.ui.base.mvi.ViewStateEvent
import com.toucanpay.ui.components.sendscanner.mvi.SendScannerResult
import com.toucanpay.ui.model.TradeInputData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SendScannerViewState(
    val inProgress: Boolean,
    val success: ViewStateEvent<TradeInputData>?,
    val error: ViewStateEvent<String>?
): MviViewState<SendScannerResult> {

    companion object {
        fun default() = SendScannerViewState(false, null, null)
    }

    override fun isSavable() = !inProgress

    override fun reduce(result: SendScannerResult): SendScannerViewState {
        return when (result) {
            is SendScannerResult.InFlight -> result.reduce()
            is SendScannerResult.Success -> result.reduce()
            is SendScannerResult.Error -> result.reduce()
        }
    }

    private fun SendScannerResult.InFlight.reduce() = this@SendScannerViewState.copy(
        inProgress = true,
        success = null,
        error = null
    )

    private fun SendScannerResult.Error.reduce() = this@SendScannerViewState.copy(
        inProgress = false,
        success = null,
        error = ViewStateEvent(errorMessage)
    )

    private fun SendScannerResult.Success.reduce() = this@SendScannerViewState.copy(
        inProgress = false,
        success = ViewStateEvent(data),
        error = null
    )
}