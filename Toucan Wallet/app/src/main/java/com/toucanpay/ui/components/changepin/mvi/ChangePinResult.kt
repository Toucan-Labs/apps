package com.toucanpay.ui.components.changepin.mvi

import com.toucanpay.ui.base.mvi.MviResult

sealed class ChangePinResult: MviResult {

    object InFlight: ChangePinResult()

    object Success: ChangePinResult()

    data class Error(val errorMessage: String): ChangePinResult()
}