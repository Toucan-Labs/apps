package com.toucanwalletdemo.ui.components.sendscanner.mvi

import com.toucanwalletdemo.ui.base.mvi.MviResult
import com.toucanwalletdemo.ui.model.TradeInputData

sealed class SendScannerResult: MviResult {

    object InFlight: SendScannerResult()

    data class Success(val data: TradeInputData): SendScannerResult()

    data class Error(val errorMessage: String): SendScannerResult()
}