package com.toucanwalletdemo.ui.components.wallets

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.wallets.mvi.WalletsAction
import com.toucanwalletdemo.ui.components.wallets.mvi.WalletsActionProcessor
import com.toucanwalletdemo.ui.components.wallets.mvi.WalletsResult

class WalletsViewModel: MviViewModel<WalletsAction, WalletsResult, WalletsViewState>(
    WalletsActionProcessor(),
    WalletsViewState.default()
) {

    override fun initialAction() = WalletsAction.GetBalancesAction
}