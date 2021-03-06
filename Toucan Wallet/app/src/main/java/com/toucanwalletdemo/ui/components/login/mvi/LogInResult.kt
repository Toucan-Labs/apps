package com.toucanwalletdemo.ui.components.login.mvi

import com.toucanwalletdemo.ui.base.mvi.MviResult

sealed class LogInResult: MviResult {

    object InFlight: LogInResult()

    data class UserLoggedIn(val isRewardClaimed: Boolean): LogInResult()

    object PINChangedSuccess: LogInResult()

    data class Error(val errorMessage: String): LogInResult()
}