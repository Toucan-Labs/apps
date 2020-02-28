package com.toucanpay.ui.components.sendscanner.mvi

import com.toucanpay.ui.base.mvi.MviResult
import com.toucanpay.ui.model.TradeInputData

sealed class SendScannerResult: MviResult {

    object InFlight: SendScannerResult()

    data class Success(val data: TradeInputData): SendScannerResult()

    data class Error(val errorMessage: String): SendScannerResult()
}