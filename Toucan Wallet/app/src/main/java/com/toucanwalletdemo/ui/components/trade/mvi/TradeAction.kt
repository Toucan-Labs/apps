package com.toucanwalletdemo.ui.components.trade.mvi

import com.toucanwalletdemo.data.models.SendTokenData
import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class TradeAction: MviAction {

    object GetFirstToken: TradeAction()

    data class SendTokensAction(val data: SendTokenData): TradeAction()
}