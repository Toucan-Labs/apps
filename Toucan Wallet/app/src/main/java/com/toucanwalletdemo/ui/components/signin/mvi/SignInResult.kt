package com.toucanwalletdemo.ui.components.signin.mvi

import com.toucanwalletdemo.ui.base.mvi.MviResult

sealed class SignInResult: MviResult {

    object InFlight: SignInResult()

    data class UserLoggedIn(val isRewardClaimed: Boolean): SignInResult()

    object MoveToResetPin: SignInResult()

    object MoveToLogInUser: SignInResult()

    data class UserLoaded(val username: String?): SignInResult()

    data class Error(val errorMessage: String): SignInResult()
}