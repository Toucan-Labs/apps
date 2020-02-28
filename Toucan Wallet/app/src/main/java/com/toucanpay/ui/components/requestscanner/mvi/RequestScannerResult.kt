package com.toucanpay.ui.components.requestscanner.mvi

import com.toucanpay.ui.base.mvi.MviResult
import com.toucanpay.ui.model.TradeInputData

sealed class RequestScannerResult: MviResult {

    object InFlight: RequestScannerResult()

    data class Success(val data: TradeInputData): RequestScannerResult()

    data class Error(val errorMessage: String): RequestScannerResult()
}