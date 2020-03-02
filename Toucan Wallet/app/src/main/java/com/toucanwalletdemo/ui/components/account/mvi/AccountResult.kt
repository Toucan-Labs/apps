package com.toucanwalletdemo.ui.components.account.mvi

import com.toucanwalletdemo.ui.base.mvi.MviResult

sealed class AccountResult: MviResult {

    object InFlight: AccountResult()

    data class Error(val errorMessage: String): AccountResult()

    data class Success(val username: String?, val referralCode: String?): AccountResult()

    object LogOutSuccess: AccountResult()
}