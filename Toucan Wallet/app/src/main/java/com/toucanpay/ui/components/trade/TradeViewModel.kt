package com.toucanpay.ui.components.trade

import com.toucanpay.data.models.SendTokenData
import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.trade.mvi.TradeAction
import com.toucanpay.ui.components.trade.mvi.TradeActionProcessor
import com.toucanpay.ui.components.trade.mvi.TradeResult
import java.math.BigDecimal

class TradeViewModel: MviViewModel<TradeAction, TradeResult, TradeViewState>(
    TradeActionProcessor(), TradeViewState.default()
) {

    lateinit var token: String

    override fun initialAction(): TradeAction? {
        if (token.isBlank()) {
            return TradeAction.GetFirstToken
        }
        return null
    }

    fun onGetToken(tokenSymbol: String) {
        token = tokenSymbol
    }

    fun onSendTokens(tokenSymbol: String, amount: BigDecimal, toAccountId: String, reference: String) =
        accept(TradeAction.SendTokensAction(SendTokenData(tokenSymbol, amount, toAccountId, reference)))
}