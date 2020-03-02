package com.toucanwalletdemo.ui.components.signup.mvi

import com.toucanwalletdemo.ui.base.mvi.MviResult

sealed class SignUpResult: MviResult {

    object InFlight: SignUpResult()

    object Success: SignUpResult()

    data class Error(val errorMessage: String): SignUpResult()
}