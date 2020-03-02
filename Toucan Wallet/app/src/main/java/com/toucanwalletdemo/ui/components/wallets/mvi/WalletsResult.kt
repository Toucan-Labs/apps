package com.toucanwalletdemo.ui.components.wallets.mvi

import com.toucanwalletdemo.ui.base.mvi.MviResult
import com.toucanwalletdemo.data.models.Balance

sealed class WalletsResult: MviResult {

    object InFlight: WalletsResult()

    data class Success(val balances: List<Balance>): WalletsResult()

    data class Error(val error: String): WalletsResult()
}