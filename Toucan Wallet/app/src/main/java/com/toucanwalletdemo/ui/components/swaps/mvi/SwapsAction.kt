package com.toucanwalletdemo.ui.components.swaps.mvi

import com.toucanwalletdemo.data.models.Token
import com.toucanwalletdemo.data.models.Trade
import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class SwapsAction: MviAction {

    object GetTradesAction: SwapsAction()

    data class RejectTradeAction(val position: Int, val trades: List<Trade>?): SwapsAction()

    data class FulfilTradeAction(val position: Int, val trades: List<Trade>?, val tokens: List<Token>?): SwapsAction()
}