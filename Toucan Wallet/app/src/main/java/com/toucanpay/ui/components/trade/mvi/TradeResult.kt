package com.toucanpay.ui.components.trade.mvi

import com.toucanpay.ui.base.mvi.MviResult
import com.toucanpay.ui.model.TradeInputData

sealed class TradeResult: MviResult {

    object InFlight: TradeResult()

    data class FirstTokenResult(val token: String): TradeResult()

    data class Success(val data: TradeInputData): TradeResult()

    data class Error(val errorMessage: String): TradeResult()

    data class TokenError(val errorMessage: String): TradeResult()
}