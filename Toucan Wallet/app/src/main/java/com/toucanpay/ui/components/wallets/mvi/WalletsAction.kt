package com.toucanpay.ui.components.wallets.mvi

import com.toucanpay.ui.base.mvi.MviAction

sealed class WalletsAction: MviAction {

    object GetBalancesAction: WalletsAction()
}