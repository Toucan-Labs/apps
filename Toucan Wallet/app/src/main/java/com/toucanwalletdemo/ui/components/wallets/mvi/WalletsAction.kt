package com.toucanwalletdemo.ui.components.wallets.mvi

import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class WalletsAction: MviAction {

    object GetBalancesAction: WalletsAction()
}