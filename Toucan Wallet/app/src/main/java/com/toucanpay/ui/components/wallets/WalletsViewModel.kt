package com.toucanpay.ui.components.wallets

import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.wallets.mvi.WalletsAction
import com.toucanpay.ui.components.wallets.mvi.WalletsActionProcessor
import com.toucanpay.ui.components.wallets.mvi.WalletsResult

class WalletsViewModel: MviViewModel<WalletsAction, WalletsResult, WalletsViewState>(
    WalletsActionProcessor(),
    WalletsViewState.default()
) {

    override fun initialAction() = WalletsAction.GetBalancesAction
}