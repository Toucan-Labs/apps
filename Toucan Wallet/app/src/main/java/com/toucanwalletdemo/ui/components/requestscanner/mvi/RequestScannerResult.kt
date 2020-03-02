package com.toucanwalletdemo.ui.components.requestscanner.mvi

import com.toucanwalletdemo.ui.base.mvi.MviResult
import com.toucanwalletdemo.ui.model.TradeInputData

sealed class RequestScannerResult: MviResult {

    object InFlight: RequestScannerResult()

    data class Success(val data: TradeInputData): RequestScannerResult()

    data class Error(val errorMessage: String): RequestScannerResult()
}