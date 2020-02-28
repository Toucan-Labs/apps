package com.toucanpay.ui.components.swaps.mvi

import com.toucanpay.data.models.Token
import com.toucanpay.data.models.Trade
import com.toucanpay.ui.base.mvi.MviResult
import com.toucanpay.ui.model.SwapError
import com.toucanpay.ui.model.SwapSuccess

sealed class SwapsResult: MviResult {

    object InFlight: SwapsResult()

    object InFlightTrades: SwapsResult()

    data class Success(val trades: List<Trade>, val tokens: List<Token>): SwapsResult()

    data class Error(val errorMessage: String): SwapsResult()

    data class TradeRequestSuccess(val swapSuccess: SwapSuccess?, val trades: List<Trade>?): SwapsResult()

    data class TradeRequestError(val swapError: SwapError): SwapsResult()
}