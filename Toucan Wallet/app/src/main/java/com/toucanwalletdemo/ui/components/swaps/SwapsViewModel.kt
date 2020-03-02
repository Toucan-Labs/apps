package com.toucanwalletdemo.ui.components.swaps

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.swaps.mvi.SwapsAction
import com.toucanwalletdemo.ui.components.swaps.mvi.SwapsActionProcessor
import com.toucanwalletdemo.ui.components.swaps.mvi.SwapsResult

class SwapsViewModel: MviViewModel<SwapsAction, SwapsResult, SwapsViewState>(
    SwapsActionProcessor(), SwapsViewState.default()
) {

    override fun initialAction(): SwapsAction? = SwapsAction.GetTradesAction

    fun onRejectTrade(position: Int) =
        accept(SwapsAction.RejectTradeAction(position, viewState.trades))

    fun onFulfilTrade(position: Int) =
        accept(SwapsAction.FulfilTradeAction(position, viewState.trades, viewState.tokens))
}