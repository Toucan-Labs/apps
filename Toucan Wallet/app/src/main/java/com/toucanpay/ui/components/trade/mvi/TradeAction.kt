package com.toucanpay.ui.components.trade.mvi

import com.toucanpay.data.models.SendTokenData
import com.toucanpay.ui.base.mvi.MviAction

sealed class TradeAction: MviAction {

    object GetFirstToken: TradeAction()

    data class SendTokensAction(val data: SendTokenData): TradeAction()
}