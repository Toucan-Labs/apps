package com.toucanwalletdemo.ui.components.changepin.mvi

import com.toucanwalletdemo.ui.base.mvi.MviResult

sealed class ChangePinResult: MviResult {

    object InFlight: ChangePinResult()

    object Success: ChangePinResult()

    data class Error(val errorMessage: String): ChangePinResult()
}