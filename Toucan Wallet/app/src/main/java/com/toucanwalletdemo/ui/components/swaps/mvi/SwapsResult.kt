package com.toucanwalletdemo.ui.components.swaps.mvi

import com.toucanwalletdemo.data.models.Token
import com.toucanwalletdemo.data.models.Trade
import com.toucanwalletdemo.ui.base.mvi.MviResult
import com.toucanwalletdemo.ui.model.SwapError
import com.toucanwalletdemo.ui.model.SwapSuccess

sealed class SwapsResult: MviResult {

    object InFlight: SwapsResult()

    object InFlightTrades: SwapsResult()

    data class Success(val trades: List<Trade>, val tokens: List<Token>): SwapsResult()

    data class Error(val errorMessage: String): SwapsResult()

    data class TradeRequestSuccess(val swapSuccess: SwapSuccess?, val trades: List<Trade>?): SwapsResult()

    data class TradeRequestError(val swapError: SwapError): SwapsResult()
}