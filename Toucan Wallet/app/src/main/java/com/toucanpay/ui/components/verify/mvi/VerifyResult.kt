package com.toucanpay.ui.components.verify.mvi

import com.toucanpay.ui.base.mvi.MviResult

sealed class VerifyResult: MviResult {

    object InFlight: VerifyResult()

    object Success: VerifyResult()

    object MoveToLogInUser: VerifyResult()

    data class Error(val errorMessage: String): VerifyResult()
}