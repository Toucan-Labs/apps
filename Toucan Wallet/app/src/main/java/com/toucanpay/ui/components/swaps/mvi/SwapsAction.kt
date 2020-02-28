package com.toucanpay.ui.components.swaps.mvi

import com.toucanpay.data.models.Token
import com.toucanpay.data.models.Trade
import com.toucanpay.ui.base.mvi.MviAction

sealed class SwapsAction: MviAction {

    object GetTradesAction: SwapsAction()

    data class RejectTradeAction(val position: Int, val trades: List<Trade>?): SwapsAction()

    data class FulfilTradeAction(val position: Int, val trades: List<Trade>?, val tokens: List<Token>?): SwapsAction()
}