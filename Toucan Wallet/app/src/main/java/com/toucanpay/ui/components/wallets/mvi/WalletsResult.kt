package com.toucanpay.ui.components.wallets.mvi

import com.toucanpay.ui.base.mvi.MviResult
import com.toucanpay.data.models.Balance

sealed class WalletsResult: MviResult {

    object InFlight: WalletsResult()

    data class Success(val balances: List<Balance>): WalletsResult()

    data class Error(val error: String): WalletsResult()
}